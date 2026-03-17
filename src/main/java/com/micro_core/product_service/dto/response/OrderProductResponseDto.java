package com.micro_core.product_service.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderProductResponseDto {
    private Long id;
    private String productName;
    private String sku;
    private BigDecimal price;
    private Boolean hasDiscount;
    private BigDecimal discountedPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
