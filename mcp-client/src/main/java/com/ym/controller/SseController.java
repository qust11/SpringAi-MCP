package com.ym.controller;


import com.ym.enums.SseMsgEnum;
import com.ym.util.SseServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author qushutao
 * @since 2025/7/25 15:36
 **/
@RestController
@RequestMapping("/sse")
public class SseController {

    @GetMapping(value = "/connect", produces = "text/event-stream")
    public SseEmitter connect(String userId) {
        return SseServer.connect(userId);
    }

    @GetMapping(value = "/sendMsg")
    public void sendMsg(@RequestParam String userId, @RequestParam String msg) {
        SseServer.sendMsg(userId, msg, SseMsgEnum.MESSAGE);
    }

    @GetMapping(value = "/sendMsgAll")
    public void sendMsgAll(@RequestParam String msg) {
        SseServer.sendMsgAll(msg);
    }
}
