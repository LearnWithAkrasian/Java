package com.imran.repository;

import com.imran.domain.Product;
import java.util.List;

public interface ProductRepository {
    List<Product> findAllProducts();
}
