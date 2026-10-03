package com.example.orderservice.model;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
public record OrderRecord(
    Long id,
    @NotBlank(message = "Product name is required") String productName,
    @Min(value = 1, message = "Quantity must be at least 1") Integer quantity,
    BigDecimal price
) {}