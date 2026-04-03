package com.shopwave.shopwave_starter.repository;
//Hawa Nursefa ATE/7005/14
import com.shopwave.shopwave_starter.model.Category;
import com.shopwave.shopwave_starter.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    void setUp() {
        // Create and save a category
        category = Category.builder()
                .name("Electronics")
                .description("Electronic devices")
                .build();
        category = categoryRepository.save(category);

        // Create and save products
        Product product1 = Product.builder()
                .name("Laptop")
                .description("Gaming laptop")
                .price(new BigDecimal("1200.00"))
                .stock(10)
                .category(category)
                .build();

        Product product2 = Product.builder()
                .name("Smartphone")
                .description("Latest model")
                .price(new BigDecimal("800.00"))
                .stock(20)
                .category(category)
                .build();

        Product product3 = Product.builder()
                .name("Tablet")
                .description("Light and portable")
                .price(new BigDecimal("400.00"))
                .stock(15)
                .category(category)
                .build();

        productRepository.saveAll(List.of(product1, product2, product3));
    }

    @Test
    void findByNameContainingIgnoreCase_ReturnsCorrectResults() {
        // When
        List<Product> products = productRepository.findByNameContainingIgnoreCase("laptop");

        // Then
        assertThat(products).hasSize(1);
        assertThat(products.get(0).getName()).isEqualTo("Laptop");

        // Case insensitive
        products = productRepository.findByNameContainingIgnoreCase("PHONE");
        assertThat(products).hasSize(1);
        assertThat(products.get(0).getName()).isEqualTo("Smartphone");
    }

    @Test
    void findByNameContainingIgnoreCase_ReturnsEmptyWhenNotFound() {
        List<Product> products = productRepository.findByNameContainingIgnoreCase("nonexistent");
        assertThat(products).isEmpty();
    }
}