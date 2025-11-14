package com.imran.repository;

import com.imran.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public class DummyProductRepositoryImpl implements ProductRepository {
    @Override
    public List<ProductDto> findAllProducts() {
        return List.of(
                new ProductDto(
                "Apple ipad",
                "Apple ipad 10.2.32GB",
                BigDecimal.valueOf(325.35)),

                new ProductDto(
                "Headphone",
                "Jabra Elite Bluetooth Headphones",
                BigDecimal.valueOf(235.55)),

                new ProductDto(
                        "Keyboard",
                        "Keychorn K3 Ultra slim",
                        BigDecimal.valueOf(635.55))
        );
    }
}
