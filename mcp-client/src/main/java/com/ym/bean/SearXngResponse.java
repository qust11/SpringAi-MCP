package com.ym.bean;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author qushutao
 * @since 2025/7/28 22:13
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearXngResponse {

    private String query;

    private List<SearchResult> results;
}
