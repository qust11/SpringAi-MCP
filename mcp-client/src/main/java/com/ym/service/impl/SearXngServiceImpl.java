package com.ym.service.impl;


import cn.hutool.json.JSONUtil;
import com.ym.bean.SearXngResponse;
import com.ym.bean.SearchResult;
import com.ym.service.ISearXngService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.util.Collections;
import java.util.List;

/**
 * @author qushutao
 * @since 2025/7/28 22:15
 **/
@Service
@Slf4j
public class SearXngServiceImpl implements ISearXngService {

    @Value("${internet.websearch.searxng.url}")
    private String SEARCH_URL;

    @Value("${internet.websearch.searxng.limit}")
    private int LIMIT;

    @Autowired
    private OkHttpClient okHttpClient;

    @Override
    public List<SearchResult> search(String query) {
        HttpUrl httpUrl = HttpUrl.get(SEARCH_URL)
                .newBuilder()
                .addQueryParameter("q", query)
                .addQueryParameter("format", "json")
                .build();
        Request request = new Request.Builder()
                .url(httpUrl)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RuntimeException("请求错误，请求发生错误");
            }

            if (null == response.body()) {
                throw new RuntimeException("请求错误返回数据为空");
            }
            String responseStr = response.body().string();
            List<SearchResult> results = JSONUtil.toBean(responseStr, SearXngResponse.class).getResults();
            return dealResult(results);
        } catch (Exception e) {
            log.error("请求错误", e);
        }
        return Collections.emptyList();
    }

    private List<SearchResult> dealResult(List<SearchResult> searchResults) {
        return searchResults.stream()
                .sorted((o1, o2) -> o2.getScore().compareTo(o1.getScore()))
                .limit(LIMIT)
                .toList();

    }
}
