package com.ym.controller;


import com.ym.bean.ChatEntity;
import com.ym.service.IChatService;
import com.ym.service.IDocService;
import com.ym.util.LeeResult;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * @author qushutao
 * @since 2025/7/25 9:02
 **/
@RestController
@RequestMapping("/rag")
@RequiredArgsConstructor
public class DocController {

    private final IDocService docService;
    private final IChatService chatService;

    @PostMapping("/uploadRagDoc")
    public LeeResult uploadRagDoc(@RequestParam("file") MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        Resource resource = file.getResource();
        List<Document> result = docService.loadDoc(originalFilename, resource);
        return LeeResult.ok(result);
    }

    @GetMapping("/doSearch")
    public LeeResult search(@RequestParam String question) {
        List<Document> result = docService.search(question);
        return LeeResult.ok(result);
    }

    @PostMapping("/search")
    public LeeResult search(@RequestBody ChatEntity chatEntity) {
        List<Document> documentList = docService.search(chatEntity.getMessage());

        Flux<String> result = chatService.doChatRagSearch(chatEntity, documentList);
        return LeeResult.ok(result);
    }
}
