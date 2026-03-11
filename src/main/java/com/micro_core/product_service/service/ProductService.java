package com.micro_core.product_service.service;

import com.micro_core.product_service.dto.request.RequestProductDto;
import com.micro_core.product_service.dto.response.ResponseProductDto;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface ProductService {

    public ResponseProductDto create(RequestProductDto requestProductDto, List<MultipartFile> images) throws IOException;
    public ResponseProductDto update(RequestProductDto requestProductDto, Long productId);
    public void delete(Long productId);
    public Page<ResponseProductDto> getAllProducts(int page, int size);
    public ResponseProductDto getProductById(Long productId);

}
