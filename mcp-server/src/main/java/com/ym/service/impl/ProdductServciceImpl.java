package com.ym.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ym.mapper.ProductMapper;
import com.ym.entity.Product;
import com.ym.service.IProductService;
import org.springframework.stereotype.Service;

/**
 * @author qushutao
 * @since 2025/8/2 9:18
 **/
@Service
public class ProdductServciceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {
}
