package com.ym.util;


import org.springframework.ai.transformer.splitter.TextSplitter;

import java.util.List;

/**
 * @author qushutao
 * @since 2025/7/27 10:13
 **/
public class CustomTextSplit extends TextSplitter {

    @Override
    protected List<String> splitText(String text) {
        return List.of(doSplit(text));
    }

    private String[] doSplit(String text) {
        return text.split("[ \\t]+\\R{2,}[ \\t]*|\\R{2,}[ \\t]+");
        //        return text.split("\\s*\\R\\s*\\R\\s*");
    }
}
