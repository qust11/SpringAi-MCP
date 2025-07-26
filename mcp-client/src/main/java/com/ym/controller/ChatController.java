package com.ym.controller;


import com.ym.bean.ChatEntity;
import com.ym.service.IChatService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

/**
 * @author qushutao
 * @since 2025/7/25 9:02
 **/
@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final IChatService chatService;
    @PostMapping( "/doChat")
    public void doChat(@RequestBody ChatEntity chatEntity){
         chatService.doChat(chatEntity);
    }

}
