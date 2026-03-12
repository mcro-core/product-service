package com.micro_core.product_service.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseProductDto {
    private long productId;
    private String productName;
    private String description;
    private BigDecimal price;
    private String skuCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ResponseProductImageDto> productImages;

}
