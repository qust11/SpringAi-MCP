package com.ym.mcp;


import com.ym.entity.Product;
import com.ym.service.IProductService;
import com.ym.toolentity.ToolProduct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author qushutao
 * @since 2025/8/2 9:42
 **/
@Component
@Slf4j
public class ProductTool {

    @Autowired
    private IProductService productService;


    @Tool(name = "createProduct",description =  "创建商品信息")
    public void createProduct(ToolProduct toolProduct) {
        log.info("createProduct:{}", toolProduct.toString());
        Product product = new Product();
        BeanUtils.copyProperties(toolProduct, product);
        productService.save(product);
    }
}
