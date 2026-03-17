package com.micro_core.product_service.api;

import com.micro_core.product_service.dto.request.RequestDiscountDto;
import com.micro_core.product_service.service.DiscountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/discount")
@RequiredArgsConstructor
@Validated
public class DiscountController {
    private final DiscountService discountService;

    @PostMapping
    public ResponseEntity<String> discountCreate(@Valid @RequestBody RequestDiscountDto requestDiscountDto) {
        discountService.createDiscount(requestDiscountDto);
        return ResponseEntity.ok("Discount created successfully!");
    }

    @PutMapping
    public ResponseEntity<String> updateDiscount(
            @Valid @RequestBody RequestDiscountDto requestDiscountDto,
            @Min(value = 1, message = "Invalid Discount ID") @RequestParam Long discountId) {
        discountService.updateDiscount(requestDiscountDto, discountId);
        return ResponseEntity.ok("Discount updated successfully!");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteDiscount(
            @Min(value = 1, message = "Invalid Discount ID") @RequestParam Long discountId) {
            discountService.deleteDiscount(discountId);
            return ResponseEntity.ok("Discount successfully deleted!");
    }
}
