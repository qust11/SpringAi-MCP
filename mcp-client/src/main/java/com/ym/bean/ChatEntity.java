package com.ym.bean;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author qushutao
 * @since 2025/7/26 9:37
 **/
@Data
public class ChatEntity {

    @Schema(description = "机器人消息ID")
    private String botMsgId;

    @Schema(description = "当前用户ID")
    private String currentUserName;

    @Schema(description = "消息")
    private String message;

}
