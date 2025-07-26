package com.ym.service.impl;


import cn.hutool.json.JSONUtil;
import com.ym.bean.ChatEntity;
import com.ym.bean.ChatFinishEntity;
import com.ym.enums.SseMsgEnum;
import com.ym.service.IChatService;
import com.ym.util.SseServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
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
            log.info("sse发送消息：{}", msg);
            return msg;
        }).toList();
        String contentStr = list.stream().collect(Collectors.joining());
        ChatFinishEntity chatFinishEntity = new ChatFinishEntity(contentStr, chatEntity.getBotMsgId());
        // 添加完成消息
        SseServer.sendMsg(chatEntity.getCurrentUserName(), JSONUtil.toJsonStr(chatFinishEntity), SseMsgEnum.FINISH);
        return content;
    }
}
