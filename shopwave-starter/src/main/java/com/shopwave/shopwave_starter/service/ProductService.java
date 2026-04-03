package com.shopwave.shopwave_starter.service;
//Hawa Nursefa ATE/7005/14
import com.shopwave.shopwave_starter.dto.CreateProductRequest;
import com.shopwave.shopwave_starter.dto.ProductDTO;
import com.shopwave.shopwave_starter.exception.ProductNotFoundException;
import com.shopwave.shopwave_starter.model.Category;
import com.shopwave.shopwave_starter.model.Product;
import com.shopwave.shopwave_starter.repository.CategoryRepository;
import com.shopwave.shopwave_starter.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(this::toDTO);
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        return toDTO(product);
    }

    public ProductDTO createProduct(CreateProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + request.getCategoryId()));

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(category)
                .build();

        product = productRepository.save(product);
        return toDTO(product);
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> searchProducts(String keyword, BigDecimal maxPrice) {
        List<Product> products;
        if (keyword != null && maxPrice != null) {
            products = productRepository.findByNameContainingIgnoreCaseAndPriceLessThanEqual(keyword, maxPrice);
        } else if (keyword != null) {
            products = productRepository.findByNameContainingIgnoreCase(keyword);
        } else if (maxPrice != null) {
            products = productRepository.findByPriceLessThanEqual(maxPrice);
        } else {
            products = productRepository.findAll();
        }
        return products.stream().map(this::toDTO).toList();
    }

    public void updateStock(Long id, int delta) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        int newStock = product.getStock() + delta;
        if (newStock < 0) {
            throw new IllegalArgumentException("Stock cannot go negative");
        }
        product.setStock(newStock);
    }

    private ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
            dto.setCategoryName(product.getCategory().getName());
        }
        return dto;
    }
}