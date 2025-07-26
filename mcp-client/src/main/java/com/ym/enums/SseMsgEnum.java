package com.ym.enums;


import lombok.Getter;

/**
 * @author qushutao
 * @since 2025/7/25 15:45
 **/
@Getter
public enum SseMsgEnum {

    MESSAGE("message", "单次发送普通消息"),
    ADD("add", "消息追加适用于流式推送"),
    CUSTOM_EVENT("custom_event", "自定义消息"),
    FINISH("finish", "完成"),
    DONE("done", "完成"),
    ;


    private final String code;

    private final String desc;

    SseMsgEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
