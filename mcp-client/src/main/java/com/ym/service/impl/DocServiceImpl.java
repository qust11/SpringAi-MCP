package com.ym.service.impl;


import cn.hutool.json.JSONUtil;
import com.ym.service.IDocService;
import com.ym.util.CustomTextSplit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.vectorstore.redis.RedisVectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qushutao
 * @since 2025/7/27 9:55
 **/
@Service
@Slf4j
public class DocServiceImpl implements IDocService {

    @Autowired
    private RedisVectorStore redisVectorStore;

    public List<Document> loadDoc(String fileName, Resource resource) {
        log.info("开始加载文档");
        // 加载文档
        TextReader textReader = new TextReader(resource);
        textReader.getCustomMetadata().put("fileName", fileName);
        List<Document> sourceDocumentList = textReader.get();

        // 切分器
        TextSplitter textSplitter = new CustomTextSplit();
        List<Document> documentList = textSplitter.apply(sourceDocumentList);
        log.info("文档加载完成:【{}】", JSONUtil.toJsonStr(documentList));

        redisVectorStore.add(documentList);
        return documentList;
    }

    @Override
    public List<Document> search(String question) {
        List<Document> documentList = redisVectorStore.similaritySearch(question);
        return documentList;
    }

}
