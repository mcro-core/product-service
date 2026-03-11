package com.micro_core.product_service.dto.response;

import lombok.*;

import java.math.BigDecimal;
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

    private List<ResponseProductImageDto> productImages;

}
