package com.ym.controller;


import com.ym.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qushutao
 * @since 2025/8/2 9:43
 **/
@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private IProductService productService;

    @GetMapping("/orm")
    private String test(){
        return productService.list().toString();
    }
}
