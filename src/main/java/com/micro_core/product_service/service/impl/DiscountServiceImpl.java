package com.micro_core.product_service.service.impl;

import com.micro_core.product_service.dto.request.RequestDiscountDto;
import com.micro_core.product_service.dto.response.DiscountResponseDto;
import com.micro_core.product_service.entity.Discount;
import com.micro_core.product_service.entity.Product;
import com.micro_core.product_service.exceptions.ResourceNotFoundException;
import com.micro_core.product_service.repo.DiscountRepo;
import com.micro_core.product_service.repo.ProductRepo;
import com.micro_core.product_service.service.DiscountService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepo discountRepo;

    @Override
    @Transactional
    public void createDiscount(RequestDiscountDto requestDiscountDto) {

        Discount discount = Discount.builder()
                .description(requestDiscountDto.getDescription())
                .discountType(requestDiscountDto.getDiscountType())
                .discountValue(requestDiscountDto.getDiscountValue())
                .startDate(requestDiscountDto.getStartDate())
                .endDate(requestDiscountDto.getEndDate())
                .isActive(requestDiscountDto.isActive())
                .build();

        discountRepo.save(discount);

    }

    @Override
    public void updateDiscount(RequestDiscountDto requestDiscountDto, Long discountId) {

        Discount selectedDiscount = discountRepo.findById(discountId)
                .orElseThrow(() -> new ResourceNotFoundException("Discount Not Found!"));
        selectedDiscount.setDescription(requestDiscountDto.getDescription());
        selectedDiscount.setDiscountValue(requestDiscountDto.getDiscountValue());
        selectedDiscount.setDiscountType(requestDiscountDto.getDiscountType());
        selectedDiscount.setStartDate(requestDiscountDto.getStartDate());
        selectedDiscount.setEndDate(requestDiscountDto.getEndDate());
        selectedDiscount.setActive(requestDiscountDto.isActive());
    }

    @Override
    public DiscountResponseDto findById(Long discountId) {

        Discount discount = discountRepo.findById(discountId)
                .orElseThrow(() -> new ResourceNotFoundException("Discount Not Found"));

        return this.mapToResponseDto(discount);
    }

    @Override
    public void deleteDiscount(Long discountID) {

        Discount selectedDiscount = discountRepo.findById(discountID)
                .orElseThrow(() -> new ResourceNotFoundException("Discount Not Found"));
        discountRepo.delete(selectedDiscount);
    }

    private DiscountResponseDto mapToResponseDto(Discount discount){

       return DiscountResponseDto.builder()
               .id(discount.getId())
               .description(discount.getDescription())
               .discountValue(discount.getDiscountValue())
               .discountType(discount.getDiscountType())
               .startDate(discount.getStartDate())
               .endDate(discount.getEndDate())
               .isActive(discount.isActive())
               .build();
    }
}
