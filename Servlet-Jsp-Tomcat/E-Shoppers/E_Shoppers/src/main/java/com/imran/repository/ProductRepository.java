package com.imran.repository;

import com.imran.domain.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAllProducts();
    Optional<Product> findProductById(Long id);
}
