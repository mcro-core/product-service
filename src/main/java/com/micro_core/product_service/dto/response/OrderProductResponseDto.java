package com.micro_core.product_service.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderProductResponseDto {
    private Long id;
    private String productName;
    private BigDecimal price;
    private Boolean hasDiscount;
    private BigDecimal discountedPrice;
}
