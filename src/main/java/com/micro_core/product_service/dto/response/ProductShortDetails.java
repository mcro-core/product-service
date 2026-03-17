package com.micro_core.product_service.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductShortDetails {
    private String productName;
    private String skuCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
