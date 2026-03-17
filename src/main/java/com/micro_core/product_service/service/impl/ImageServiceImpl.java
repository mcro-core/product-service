package com.micro_core.product_service.service.impl;


import com.micro_core.product_service.entity.ProductImages;
import com.micro_core.product_service.repo.ProductImageRepo;
import com.micro_core.product_service.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {

    private final ProductImageRepo productImageRepo;

    @Override
    public void updateProductImages(List<MultipartFile> images) {
       log.info("Updating {} product images", images.size());

        for(MultipartFile file : images){
            String originalFileName = file.getOriginalFilename();
            if(originalFileName == null || originalFileName.isEmpty()){
                continue;
            }

            try{
                String idString = originalFileName.contains(".")
                        ?originalFileName.substring(0, originalFileName.lastIndexOf("."))
                        : originalFileName;

                Long imageId = Long.parseLong(idString);

                ProductImages existingImage = this.findProductImage(imageId);

                existingImage.setImage(file.getBytes());

                productImageRepo.save(existingImage);

            }catch (NumberFormatException e){
                log.error("Invalid image ID format in filename: {}", originalFileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public void deleteProductImage(Long imageId) {
        findProductImage(imageId);
        productImageRepo.deleteById(imageId);
        log.info("Image ID {} deleted successfully",imageId);
    }

    private ProductImages findProductImage(Long imageId){
            return productImageRepo.findById(imageId)
                    .orElseThrow(()-> new RuntimeException("Image not found with id" + imageId));
    }
}
