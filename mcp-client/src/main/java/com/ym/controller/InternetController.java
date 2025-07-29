package com.ym.controller;


import com.ym.bean.ChatEntity;
import com.ym.service.IChatService;
import com.ym.service.ISearXngService;
import com.ym.util.LeeResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author qushutao
 * @since 2025/7/25 9:02
 **/
@RestController
@RequestMapping("/internet")
@RequiredArgsConstructor
public class InternetController {
    private final IChatService chatService;

    private final ISearXngService searXngService;
    @GetMapping( "/search/test")
    public LeeResult searchTest(@RequestParam String query){
        return LeeResult.ok(searXngService.search(query));
    }

    @PostMapping( "/search")
    public LeeResult search(@RequestBody ChatEntity chatEntity){
        return LeeResult.ok(chatService.doInternetSearch(chatEntity));
    }

}
