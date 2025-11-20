package com.imran.service;

import com.imran.domain.Product;
import com.imran.dto.ProductDto;
import com.imran.repository.ProductRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ProductServImpl implements ProductService {

    public ProductRepository productRepository;
    public ProductServImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> findAllProductSortedByName() {
        return productRepository.findAllProducts()
                .stream() // Converting List to a stream
                .map(this::convertToDTO)// Converting each Product entity into a ProductDto using the convertToDTO method
                .sorted(Comparator.comparing(ProductDto::getName)) // Sorting based on products name
                .collect(Collectors.toList()); // collecting the stream back into list.
    }

    private ProductDto convertToDTO(Product product) {
        return new ProductDto(
                product.getId(),
                product.getProductName(),
                product.getProductDescription(),
                product.getProductPrice()
        );
    }
}
