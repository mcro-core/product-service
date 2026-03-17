package com.micro_core.product_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;


import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestProductDto {
    @NotBlank(message = "Product name is required")
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters")
    private String productName;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotBlank(message = "SKU Code is required")
    private String skuCode;

    @NotNull(message = "Initial quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;
}
