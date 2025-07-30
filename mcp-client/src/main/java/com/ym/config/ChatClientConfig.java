package com.ym.config;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author qushutao
 * @since 2025/7/25 10:14
 **/
@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ToolCallbackProvider toolCallbackProvider){
        return chatClientBuilder
                .defaultToolCallbacks(toolCallbackProvider)
                .defaultSystem("你是一个非常棒的人工智能助手，可以帮我解决很多问题，你的名字叫做'Lagogo'")

                .build();
    }

}
