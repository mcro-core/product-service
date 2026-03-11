package com.micro_core.product_service.api;

import com.micro_core.product_service.service.ImageService;
import com.micro_core.product_service.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductImageController {

    private final ImageService imageService;

    @PutMapping(value = "/images/bulk-update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateImages(
            @RequestPart("images")List<MultipartFile> images
            ){
            imageService.updateProductImages(images);
            return ResponseEntity.ok("Selected images updated successfully!");
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<String> deleteImage(@PathVariable Long imageId){
        imageService.deleteProductImage(imageId);
        return ResponseEntity.ok("deleted successfully");
    }
}
