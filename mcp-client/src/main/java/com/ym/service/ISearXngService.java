package com.ym.service;


import com.ym.bean.SearchResult;

import java.util.List;

/**
 * @author qushutao
 * @since 2025/7/28 22:15
 **/
public interface ISearXngService {

    List<SearchResult> search(String query);
}
