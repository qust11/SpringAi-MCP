package com.ym.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qushutao
 * @since 2025/7/26 10:16
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatFinishEntity {

    private String message;

    private String botMsgId;
}
