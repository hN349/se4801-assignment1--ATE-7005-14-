package com.shopwave.shopwave_starter.repository;
//Hawa Nursefa ATE/7005/14
import com.shopwave.shopwave_starter.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByPriceLessThanEqual(BigDecimal maxPrice);
    List<Product> findByNameContainingIgnoreCase(String keyword);
    Optional<Product> findTopByOrderByPriceDesc();
    // Combined query for search
    List<Product> findByNameContainingIgnoreCaseAndPriceLessThanEqual(String keyword, BigDecimal maxPrice);
}