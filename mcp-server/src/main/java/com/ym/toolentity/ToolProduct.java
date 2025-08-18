package com.ym.toolentity;


import lombok.Data;
import org.springframework.ai.tool.annotation.ToolParam;

import java.time.LocalDateTime;

/**
 * @author qushutao
 * @since 2025/8/2 9:14
 **/
@Data
public class ToolProduct {

    @ToolParam(description = "商品名称")
    private String productName;

    @ToolParam(description = "商品品牌")
    private String brand;

    @ToolParam(description = "商品价格 单位：分")
    private Integer price;

    @ToolParam(description = "商品库存")
    private Integer stock;

    @ToolParam(description = "商品描述")
    private String description;

    @ToolParam(description = "商品状态 0:下架 1:上架")
    private Integer status;

}
