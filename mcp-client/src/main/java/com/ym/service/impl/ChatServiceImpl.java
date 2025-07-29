package com.ym.service.impl;


import cn.hutool.json.JSONUtil;
import com.ym.bean.ChatEntity;
import com.ym.bean.ChatFinishEntity;
import com.ym.bean.SearchResult;
import com.ym.enums.SseMsgEnum;
import com.ym.service.IChatService;
import com.ym.service.ISearXngService;
import com.ym.util.SseServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author qushutao
 * @since 2025/7/25 10:08
 **/
@Service
@Slf4j
public class ChatServiceImpl implements IChatService {

    @Autowired
    private ChatClient chatClient;
    @Autowired
    private ISearXngService searXngService;


    public String chat(String msg) {
        return chatClient.prompt(msg).call().content();
    }

    @Override
    public Flux<ChatResponse> streamChat(String msg) {
        return chatClient.prompt(msg).stream().chatResponse();
    }

    @Override
    public Flux<String> streamChatStr(String msg) {
        return chatClient.prompt(msg).stream().content();
    }

    @Override
    public Flux<String> doChat(ChatEntity chatEntity) {
        Flux<String> content = chatClient.prompt(chatEntity.getMessage()).stream().content();
        List<String> list = content.toStream().map(msg -> {
            SseServer.sendMsg(chatEntity.getCurrentUserName(), msg, SseMsgEnum.ADD);
            return msg;
        }).toList();
        String contentStr = list.stream().collect(Collectors.joining());
        ChatFinishEntity chatFinishEntity = new ChatFinishEntity(contentStr, chatEntity.getBotMsgId());
        // 添加完成消息
        SseServer.sendMsg(chatEntity.getCurrentUserName(), JSONUtil.toJsonStr(chatFinishEntity), SseMsgEnum.FINISH);
        return content;
    }

    private final String RAG_PORMPT = """
            基于上下文的知识库来回答问题：
            【上下文】
            {context}
            
            【问题】
            [question]
            
            【输出】
            如果没询到回复：不知道。
            如果查到请回复具体的内容。不相关的近似内容不必提到。
            """;

    @Override
    public Flux<String> doChatRagSearch(ChatEntity chatEntity, List<Document> documentList) {
        String question = chatEntity.getMessage();
        if (StringUtils.isBlank(question)) {
            return null;
        }

        String content = documentList.stream().map(Document::getText).collect(Collectors.joining("\n"));
        String prompt = RAG_PORMPT.replace("{context}", content).replace("[question]", question);
        chatEntity.setMessage(prompt);
        Flux<String> stringFlux = doChat(chatEntity);
        return stringFlux;
    }

    private static final String INTERNET_PROMPT = """
            你是一个文本总结高手，请基于上下文信息根据问题进行总结并返回：
            【上下文】
            [context]
            
            【问题】
            [question]
            
            【输出】
            如果没询到回复：不知道。
            如果查到请回复具体内容。
            """;

    @Override
    public Flux<String> doInternetSearch(ChatEntity chatEntity) {
        String question = chatEntity.getMessage();
        List<SearchResult> searchResults = searXngService.search(question);

        String prompt = buildSearxngPrompt(question, searchResults);

        chatEntity.setMessage(prompt);
        return doChat(chatEntity);
    }


    private String buildSearxngPrompt(String question, List<SearchResult> searchResults) {
        StringBuilder stringBuilder = new StringBuilder();
        searchResults.forEach(item -> {
            stringBuilder.append(String.format("<context>\n [标题] %s  \n [来源]： %s  \n [摘要] %s </context> \n", item.getTitle(), item.getUrl(), item.getContent()));
        });
        return INTERNET_PROMPT.replace("[question]", question).replace("[context]", stringBuilder.toString());
    }
}
