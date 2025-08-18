package com.ym.mcp;


import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;
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

import javax.swing.plaf.multi.MultiTableUI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

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
    private static Map<String, String> emailMap = new HashMap();

    static {
        emailMap.put("渠述涛", "615955994@qq.com");
        emailMap.put("小辣椒", "1364034658@qq.com");
        emailMap.put("帅哥", "m18310052361@163.com");

    }

    @Tool(name = "send email", description = "发送邮件信息至收信人")
    public void sendEmail(EmailEntity emailEntity) {
        log.info("开始发送邮件,mail = 【{}】", emailEntity.toString());
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMailMessage = new MimeMessageHelper(mimeMessage);
        try {
            mimeMailMessage.setFrom(from);
            mimeMailMessage.setTo(emailEntity.getReceiver());
            mimeMailMessage.setSubject(emailEntity.getSubject());
            mimeMailMessage.setText(converToHtml(emailEntity.getMsg()), true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("发送邮件失败,mail = 【{}】", emailEntity.toString());
            e.printStackTrace();
        }
    }

    @Tool(name = "get my email", description = "获取我的邮箱")
    public String getMyEmail(EmailEntity emailEntity) {
        return "615955994@qq.com";
    }


    @Tool(name = "get email by username", description = "通过用户名获取邮箱")
    public String getEmailByUsername(String username) {
        return emailMap.get(username);
    }

    private String converToHtml(String text) {
        MutableDataSet options = new MutableDataSet();
        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        return renderer.render(parser.parse(text));
    }
}
