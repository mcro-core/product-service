package com.micro_core.product_service.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseProductImageDto {
    private Long imageId;
    private String base64Image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
