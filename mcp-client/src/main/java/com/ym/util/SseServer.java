package com.ym.util;


import com.ym.enums.SseMsgEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @author qushutao
 * @since 2025/7/25 15:07
 **/
@Slf4j
public class SseServer {

    private static Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public static SseEmitter connect(String userId) {
        SseEmitter sseEmitter = new SseEmitter(0L);

        sseEmitter.onTimeout(timeoutCallback(userId));
        sseEmitter.onCompletion(completionCallback(userId));
        sseEmitter.onError(errorCallback(userId));
        emitters.put(userId, sseEmitter);
        log.info("【SSE】用户【{}】连接成功", userId);
        return sseEmitter;
    }

    public static void sendMsg(String userId, String msg,SseMsgEnum sseMsgEnum) {
        SseEmitter sseEmitter = emitters.get(userId);
        if (null == sseEmitter) {
            return;
        }
        sendSseMessage(sseEmitter, userId, msg, sseMsgEnum);
        log.info("【SSE】用户【{}】发送消息【{}】成功", userId, msg);
    }

    private static void sendSseMessage(SseEmitter sseEmitter, String userId, String msg, SseMsgEnum sseMsgEnum) {
        try {
            SseEmitter.SseEventBuilder event = SseEmitter.event().id(userId).data(msg).name(sseMsgEnum.getCode());
            sseEmitter.send(event);
        } catch (IOException e) {
            log.error("【SSE】用户【{}】发送消息异常", userId);
            emitters.remove(userId);
        }
    }

    private static Consumer<Throwable> errorCallback(String userId) {
        return throwable -> {
            log.error("【SSE】用户【{}】连接异常", userId);
            emitters.remove(userId);
        };
    }

    private static Runnable completionCallback(String userId) {
        return () -> {
            log.info("【SSE】用户【{}】完成", userId);
        };
    }


    public static Runnable timeoutCallback(String userId) {
        return () -> {
            log.warn("用户【{}】连接超时", userId);
            emitters.remove(userId);
        };
    }

    public static void remove(String userId) {
        emitters.remove(userId);
    }


    /**
     * SSE发送消息给所有用户
     * @param msg 消息
     */
    public static void sendMsgAll(String msg) {
        for (Map.Entry<String, SseEmitter> entry : emitters.entrySet()) {
            sendSseMessage(entry.getValue(), entry.getKey(), msg, SseMsgEnum.MESSAGE);
        }
        log.info("【SSE】发送消息【{}】给所有用户成功", msg);
    }
}
