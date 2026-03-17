package com.micro_core.product_service.dto.response;

import com.micro_core.product_service.entity.Product;
import com.micro_core.product_service.enums.DiscountType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiscountResponseDto {

    private Long id;
    private String description;
    private BigDecimal discountValue;
    private DiscountType discountType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean isActive;
    private List<Product> product;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
