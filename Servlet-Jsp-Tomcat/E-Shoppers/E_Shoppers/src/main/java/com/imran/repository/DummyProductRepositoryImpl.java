package com.imran.repository;

import com.imran.domain.Product;

import java.math.BigDecimal;
import java.util.List;

public class DummyProductRepositoryImpl implements ProductRepository {
    private static final List<Product> ALL_PRODUCTS = List.of(
            new Product(
                    1L,
                    "Apple ipad",
                    "Apple ipad 10.2.32GB",
                    BigDecimal.valueOf(325.35)),

            new Product(
                    2L,
                    "Headphone",
                    "Jabra Elite Bluetooth Headphones",
                    BigDecimal.valueOf(235.55)),

            new Product(
                    3L,
                    "Keyboard",
                    "Keychorn K3 Ultra slim",
                    BigDecimal.valueOf(635.55)),
            new Product(
                    4L,
                    "Microsoft Surface",
                    "Surface Pro Max",
                    BigDecimal.valueOf(777.99))
    );
    @Override
    public List<Product> findAllProducts() {
        return ALL_PRODUCTS;
    }
}
