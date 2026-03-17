package com.micro_core.product_service.api;

import com.micro_core.product_service.service.ImageService;
import com.micro_core.product_service.service.ProductService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
@Validated
public class ProductImageController {

    private final ImageService imageService;

    @PutMapping(value = "/images/bulk-update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateImages(
            @NotEmpty(message = "Please select at least one image to update")
            @RequestPart("images")List<MultipartFile> images
            ){
            imageService.updateProductImages(images);
            return ResponseEntity.ok("Selected images updated successfully!");
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<String> deleteImage(
            @Min(value = 1, message = "Invalid Image ID")
            @PathVariable Long imageId){
        imageService.deleteProductImage(imageId);
        return ResponseEntity.ok("deleted successfully");
    }
}
