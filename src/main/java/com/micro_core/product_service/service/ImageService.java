package com.micro_core.product_service.service;


import com.micro_core.product_service.dto.response.ResponseProductImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    public void updateProductImages(List<MultipartFile> images);
    public void deleteProductImage(Long imageId);
}
