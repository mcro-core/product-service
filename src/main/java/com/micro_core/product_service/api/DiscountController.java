package com.micro_core.product_service.api;

import com.micro_core.product_service.dto.request.RequestDiscountDto;
import com.micro_core.product_service.service.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/discount")
@RequiredArgsConstructor
public class DiscountController {
    private final DiscountService discountService;

    @PostMapping
    public ResponseEntity<String> discountCreate(RequestDiscountDto requestDiscountDto){
        discountService.createDiscount(requestDiscountDto);
        return ResponseEntity.ok("Discount created !");
    }

    @PutMapping
    public ResponseEntity<String> updateDiscount(RequestDiscountDto requestDiscountDto, @RequestParam Long discountId){
        discountService.updateDiscount(requestDiscountDto, discountId);
        return ResponseEntity.ok("Discount updated");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteDiscount(@RequestParam  Long discountId){
        discountService.deleteDiscount(discountId);
        return ResponseEntity.ok("Discount successfully deleted!");
    }
}
