package com.ym.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ai.tool.annotation.ToolParam;

/**
 * @author qushutao
 * @since 2025/7/30 14:35
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailEntity {

    @ToolParam(description = "邮件内容")
    private String msg;

    @ToolParam(description = "收件人")
    private String receiver;

    @ToolParam(description = "主题")
    private String subject;
}
