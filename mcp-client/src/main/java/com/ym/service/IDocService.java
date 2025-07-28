package com.ym.service;


import org.springframework.ai.document.Document;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * @author qushutao
 * @since 2025/7/27 9:54
 **/
public interface IDocService {

    List<Document> loadDoc(String fileName, Resource resource);

    List<Document> search(String question);
}
