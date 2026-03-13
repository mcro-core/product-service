package com.micro_core.product_service.service;

import com.micro_core.product_service.dto.request.RequestDiscountDto;
import com.micro_core.product_service.dto.response.DiscountResponseDto;
import com.micro_core.product_service.entity.Discount;

public interface DiscountService {
    public void createDiscount(RequestDiscountDto requestDiscountDto);
    public void updateDiscount(RequestDiscountDto requestDiscountDto, Long discountId);
    public DiscountResponseDto findById(Long discountId);
    public void deleteDiscount(Long discountID);
    public Discount getDiscount(Long discountId);
}
