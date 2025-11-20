package com.imran.service;

import com.imran.domain.Product;
import com.imran.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductServImplTest {
    private static final Product AppleIPad
            = new Product(
                    1L,
                    "Apple ipad",
            "Apple ipad 10.2.32GB",
            BigDecimal.valueOf(325.35)
    );

    private static final Product Headphone
            = new Product(
                    2L,
            "Headphone",
            "Jabra Elite Bluetooth Headphones",
            BigDecimal.valueOf(235.55)
    );

    private ProductRepository productRepository;

    private ProductService productService; // Service class we are testing

    /**
     * This method runs before each test case.
     * - It creates a mock ProductRepository using Mockito.
     * - It initializes the ProductService implementation with the mocked repository.
     */
    @BeforeEach
    public void setUp() throws Exception {
        productRepository = mock(ProductRepository.class); // Create mock repository
        productService = new ProductServImpl(productRepository); // Inject mock into service
    }

    /**
     * Test case: Verify that the method findAllProductSortedByName
     * correctly sorts products by name in ascending order.
     */
    @Test
    public void testFindAllProductSortedByName() {
        // Arrange: Define behavior of mock repository
        // When findAllProducts() is called, it will return a list containing AppleIPad and Headphone
        when(productRepository.findAllProducts())
                .thenReturn(List.of(AppleIPad, Headphone));

        // Act: Call the method to test
        var sortedByName = productService.findAllProductSortedByName();

        /*
         * Assert: Check that the products are correctly sorted by name
         */
        Assertions.assertEquals(AppleIPad.getProductName(), sortedByName.get(0).getName());
        Assertions.assertEquals(Headphone.getProductName(), sortedByName.get(1).getName());
    }
}
