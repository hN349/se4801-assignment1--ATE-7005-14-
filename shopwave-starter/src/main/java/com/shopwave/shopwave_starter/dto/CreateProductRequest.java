package com.shopwave.shopwave_starter.dto;
//Hawa Nursefa ATE/7005/14
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class CreateProductRequest {
    @NotBlank
    private String name;

    private String description;   // was missing

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @Min(0)
    private Integer stock;

    @NotNull
    private Long categoryId;      // was missing
}