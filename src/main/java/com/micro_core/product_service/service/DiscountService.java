package com.micro_core.product_service.service;

import com.micro_core.product_service.dto.request.RequestDiscountDto;
import com.micro_core.product_service.dto.response.DiscountResponseDto;

public interface DiscountService {
    public void createDiscount(RequestDiscountDto requestDiscountDto);
    public void updateDiscount(RequestDiscountDto requestDiscountDto, Long discountId);
    public DiscountResponseDto findById(Long discountId);
    public void deleteDiscount(Long discountID);
}
