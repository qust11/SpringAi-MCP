package com.ym.mcp;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.definition.ToolDefinition;
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
public class DateTool {


    @Tool(name = "get time", description = "获取指定城市当前时间")
    public String getTime(String cityName, String zoneId) {
        ZoneId zone = ZoneId.of(zoneId);
        LocalDateTime now = LocalDateTime.now(zone);
        log.info("【工具】获取城市【{}】当前时间， 【{}】", cityName, now);
        return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
