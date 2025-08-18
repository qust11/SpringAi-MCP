package com.ym;


import com.ym.mcp.DateTool;
import com.ym.mcp.EmailTool;
import com.ym.mcp.ProductTool;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author qushutao
 * @since 2025/7/30 13:27
 **/
@SpringBootApplication
@MapperScan("com.ym.mapper")
public class McpServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(McpServerApplication.class, args);
    }

    @Bean
    public ToolCallbackProvider mcpTool(DateTool dateTool, EmailTool emailTool, ProductTool productTool) {
        return MethodToolCallbackProvider.builder().toolObjects(dateTool, emailTool, productTool).build();
    }
}