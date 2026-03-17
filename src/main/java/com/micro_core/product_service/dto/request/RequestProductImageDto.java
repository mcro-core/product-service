package com.micro_core.product_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestProductImageDto {
    @NotNull(message = "Image ID is required")
    @Min(value = 1, message = "Invalid Image ID")
    private Long imageId;
}
