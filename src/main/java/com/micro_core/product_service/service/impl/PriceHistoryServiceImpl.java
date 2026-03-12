package com.micro_core.product_service.service.impl;

import com.micro_core.product_service.dto.request.RequestPriceHistoryDto;
import com.micro_core.product_service.dto.response.ResponseHistoryPriceDto;
import com.micro_core.product_service.dto.response.ResponseProductDto;
import com.micro_core.product_service.dto.response.ResponseProductImageDto;
import com.micro_core.product_service.entity.PriceHistory;
import com.micro_core.product_service.entity.Product;
import com.micro_core.product_service.exceptions.ResourceNotFoundException;
import com.micro_core.product_service.repo.PriceHistoryRepo;
import com.micro_core.product_service.repo.ProductRepo;
import com.micro_core.product_service.service.PriceHistoryService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PriceHistoryServiceImpl implements PriceHistoryService {

    private final PriceHistoryRepo priceHistoryRepo;
    private final ProductRepo productRepo;

    @Override
    @Transactional
    public boolean createPriceHistory(RequestPriceHistoryDto requestPriceHistoryDto) {
        try{
            Product product = productRepo.findById(requestPriceHistoryDto.getProductId())
                    .orElseThrow(()-> new ResourceNotFoundException("Product Not Found"));

            log.info("Creating new price history for {}", requestPriceHistoryDto.getProductId());

            PriceHistory priceHistory = PriceHistory.builder()
                    .olderPrice(requestPriceHistoryDto.getOlderPrice())
                    .newPrice(requestPriceHistoryDto.getNewPrice())
                    .product(product)
                    .build();
            priceHistoryRepo.save(priceHistory);
            return true;
        }catch(Exception exception){
            log.error("Error saving price history: {}", exception.getMessage());
            throw exception;

        }
    }


    @Override
    public Page<ResponseHistoryPriceDto> getAllPriceHistory(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PriceHistory>  priceHistoryPage = priceHistoryRepo.findAll(pageable);
        return priceHistoryPage.map(this::mapToResponseDto);
    }

    private ResponseHistoryPriceDto mapToResponseDto(PriceHistory priceHistory){
        return ResponseHistoryPriceDto.builder()
                .id(priceHistory.getId())
                .newPrice(priceHistory.getNewPrice())
                .olderPrice(priceHistory.getOlderPrice())
                .updateAt(priceHistory.getUpdatedAt())
                .createdAt(priceHistory.getCreatedAt())
                .product(priceHistory.getProduct())
                .build();
    }
}
