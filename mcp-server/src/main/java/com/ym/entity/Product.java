package com.ym.entity;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author qushutao
 * @since 2025/8/2 9:14
 **/
@Data
public class Product {

    private String productName;

    private String productId;

    private String brand;

    private Integer price;

    private Integer stock;

    private String description;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
