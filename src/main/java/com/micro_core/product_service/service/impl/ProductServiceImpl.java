package com.micro_core.product_service.service.impl;

import com.micro_core.product_service.dto.request.RequestProductDto;
import com.micro_core.product_service.dto.response.ResponseProductDto;
import com.micro_core.product_service.dto.response.ResponseProductImageDto;
import com.micro_core.product_service.entity.Product;
import com.micro_core.product_service.entity.ProductImages;
import com.micro_core.product_service.repo.ProductRepo;
import com.micro_core.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;

    @Override
    @Transactional
    public ResponseProductDto create(RequestProductDto requestProductDto, List<MultipartFile> images) throws IOException {
        log.info("Creating a new product: {}", requestProductDto.getProductName());

        Product product = Product.builder()
                .productName(requestProductDto.getProductName())
                .description(requestProductDto.getDescription())
                .price(requestProductDto.getPrice())
                .skuCode(requestProductDto.getSkuCode())
                .images(new ArrayList<>())
                .build();

        if(images != null && !images.isEmpty()){
            for (MultipartFile file : images){
                ProductImages imageEntity = ProductImages.builder()
                        .image(file.getBytes())
                        .product(product)
                        .build();

                product.getImages().add(imageEntity);
            }

        }
        Product savedProduct =  productRepo.save(product);
        log.info("Product and {} images saves successfully!", images != null ? images.size() : 0);
        return this.mapToResponseDto(savedProduct);
    }

    @Override
    public ResponseProductDto update(RequestProductDto requestProductDto, Long productId) {
            Product existingProduct = this.findProduct(productId);

            existingProduct.setProductName(requestProductDto.getProductName());
            existingProduct.setDescription(requestProductDto.getDescription());
            existingProduct.setPrice(requestProductDto.getPrice());
            existingProduct.setSkuCode(requestProductDto.getSkuCode());

            Product updatedProduct =  productRepo.save(existingProduct);
            return this.mapToResponseDto(updatedProduct);
    }

    @Override
    public void delete(Long productId) {
        Product existingProduct = this.findProduct(productId);
        productRepo.delete(existingProduct);
    }

    @Override
    public Page<ResponseProductDto> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepo.findAll(pageable);
        return productPage.map(this::mapToResponseDto);
    }

    @Override
    public ResponseProductDto getProductById(Long productId) {
        log.info("Fetching product details for ID: {}", productId);

        Product product = this.findProduct(productId);

        return this.mapToResponseDto(product);
    }

    private Product findProduct(Long productId){
        return productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id" + productId));
    }

    private ResponseProductDto mapToResponseDto(Product product){
        return ResponseProductDto.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .price(product.getPrice())
                .skuCode(product.getSkuCode())
                .productImages(product.getImages().stream()
                        .map(img -> ResponseProductImageDto.builder()
                                .imageId(img.getImageId())
                                .base64Image(Base64.getEncoder().encodeToString(img.getImage()))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
