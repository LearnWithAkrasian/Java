package com.imran.service;

import com.imran.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> findAllProductSortedByName();
}
