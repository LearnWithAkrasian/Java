package com.imran.repository;

import com.imran.dto.ProductDto;

import java.util.List;

public interface ProductRepository {
    List<ProductDto> findAllProducts();
}
