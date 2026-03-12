package com.micro_core.product_service.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestPriceHistoryDto {
    private long productId;
    private BigDecimal olderPrice;
    private BigDecimal newPrice;
}
