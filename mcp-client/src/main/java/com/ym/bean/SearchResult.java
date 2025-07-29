package com.ym.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qushutao
 * @since 2025/7/28 22:13
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchResult {

    private String title;

    private String content;

    private String url;

    private Double score;
}
