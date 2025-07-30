package com.ym.mcp;


import com.ym.entity.EmailEntity;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author qushutao
 * @since 2025/7/30 13:45
 **/
@Component
@Slf4j
public class EmailTool {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String from;

    @Tool(name = "发送邮件", description = "发送邮件")
    private void sendEmail(EmailEntity emailEntity) {
        log.info("开始发送邮件,mail = 【{}】",emailEntity.toString());
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMailMessage = new MimeMessageHelper(mimeMessage);
        try {
            mimeMailMessage.setFrom(from);
            mimeMailMessage.setTo(emailEntity.getReceiver());
            mimeMailMessage.setSubject(emailEntity.getSubject());
            mimeMailMessage.setText(emailEntity.getMsg());
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("发送邮件失败,mail = 【{}】",emailEntity.toString());
            e.printStackTrace();
        }
    }
}
