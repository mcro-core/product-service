package com.micro_core.product_service.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestPriceHistoryDto {
    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Older price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price cannot be negative")
    private BigDecimal olderPrice;

    @NotNull(message = "New price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "New price must be greater than 0")
    private BigDecimal newPrice;
}
