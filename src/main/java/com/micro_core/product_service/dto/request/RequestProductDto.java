package com.micro_core.product_service.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestProductDto {
    private String productName;
    private String description;
    private BigDecimal price;
    private String skuCode;
    private Integer quantity;
}
