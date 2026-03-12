package com.micro_core.product_service.dto.response;

import com.micro_core.product_service.entity.Product;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseHistoryPriceDto {
    private long id;
    private BigDecimal olderPrice;
    private BigDecimal newPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private Product product;
}
