package com.micro_core.product_service.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductShortDetails {
    private String productName;
    private String sku;
}
