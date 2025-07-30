package com.ym.controller;


import com.ym.service.IChatService;
import com.ym.service.IMcpService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author qushutao
 * @since 2025/7/25 9:02
 **/
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class HelloWordController {

    private final IChatService chatService;
    private final IMcpService mcpService;

    @GetMapping( "/test")
    public String hello(@RequestParam String msg){
        return "hello world " + msg;
    }
    @GetMapping( "")
    public String chat(@RequestParam String msg){
        return chatService.chat( msg);
    }

    @GetMapping( "/stream")
    public Flux<ChatResponse> streamChat(@RequestParam String msg, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        return chatService.streamChat( msg);
    }



    @GetMapping( "/stream/str")
    public Flux<String> streamChatStr(@RequestParam String msg, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        return chatService.streamChatStr( msg);
    }
    @GetMapping( "/mcp/test")
    public void test(){
        mcpService.test();
    }
}
