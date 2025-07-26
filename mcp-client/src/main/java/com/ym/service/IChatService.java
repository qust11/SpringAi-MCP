package com.ym.service;


import com.ym.bean.ChatEntity;
import org.springframework.ai.chat.model.ChatResponse;
import reactor.core.publisher.Flux;

/**
 * @author qushutao
 * @since 2025/7/25 10:07
 **/
public interface IChatService {
    /**
     * 大模型交互聊天
     * @param msg 提问信息
     * @return 响应数据
     */
    String chat(String msg);

    /**
     * 大模型流式交互聊天
     * @param msg
     * @return
     */
    Flux<ChatResponse> streamChat(String msg);

    Flux<String> streamChatStr(String msg);

    /**
     * 自定义大模型交互聊天
     * @param chatEntity 提问信息 包含消息和userId
     * @return 返回数据
     */
    Flux<String> doChat(ChatEntity chatEntity);
}
