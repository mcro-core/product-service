package com.micro_core.product_service.api;

import com.micro_core.product_service.dto.request.RequestPriceHistoryDto;
import com.micro_core.product_service.dto.response.ResponseHistoryPriceDto;
import com.micro_core.product_service.dto.response.ResponseProductDto;
import com.micro_core.product_service.service.PriceHistoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/price-history")
@AllArgsConstructor
@Validated
public class ProductPriceHistoryController {

    private final PriceHistoryService priceHistoryService;

    @PostMapping
    public ResponseEntity<Boolean> productPriceHistoryCrate(
            @Valid @RequestBody RequestPriceHistoryDto requestPriceHistoryDto) {
        boolean isCreated = priceHistoryService.createPriceHistory(requestPriceHistoryDto);
        return new ResponseEntity<>(isCreated, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ResponseHistoryPriceDto>> getAllPriceHistory(
            @Min(value = 0, message = "Page index cannot be negative")
            @RequestParam(defaultValue = "0") int page,

            @Min(value = 1, message = "Page size must be at least 1")
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(priceHistoryService.getAllPriceHistory(page, size));
    }
}
