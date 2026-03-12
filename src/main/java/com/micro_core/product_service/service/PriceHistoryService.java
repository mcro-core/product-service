package com.micro_core.product_service.service;

import com.micro_core.product_service.dto.request.RequestPriceHistoryDto;
import com.micro_core.product_service.dto.response.ResponseHistoryPriceDto;
import com.micro_core.product_service.dto.response.ResponseProductDto;
import org.springframework.data.domain.Page;

public interface PriceHistoryService {

    public boolean createPriceHistory(RequestPriceHistoryDto requestPriceHistoryDto);
    public Page<ResponseHistoryPriceDto> getAllPriceHistory(int page, int size);
}
