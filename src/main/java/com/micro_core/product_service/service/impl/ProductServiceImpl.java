package com.micro_core.product_service.service.impl;

import com.micro_core.product_service.dto.request.RequestPriceHistoryDto;
import com.micro_core.product_service.dto.request.RequestProductDto;
import com.micro_core.product_service.dto.request.StockRequestDto;
import com.micro_core.product_service.dto.response.OrderProductResponseDto;
import com.micro_core.product_service.dto.response.ProductShortDetails;
import com.micro_core.product_service.dto.response.ResponseProductDto;
import com.micro_core.product_service.dto.response.ResponseProductImageDto;
import com.micro_core.product_service.entity.Discount;
import com.micro_core.product_service.entity.Product;
import com.micro_core.product_service.entity.ProductImages;
import com.micro_core.product_service.enums.DiscountType;
import com.micro_core.product_service.exceptions.ResourceNotFoundException;
import com.micro_core.product_service.repo.ProductRepo;
import com.micro_core.product_service.service.DiscountService;
import com.micro_core.product_service.service.PriceHistoryService;
import com.micro_core.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final WebClient.Builder webClientBuilder;
    private final PriceHistoryService priceHistoryService;
    private final DiscountService discountService;

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

        StockRequestDto stockRequest = StockRequestDto.builder()
                .productId(savedProduct.getId())
                .quantity(requestProductDto.getQuantity())
                .build();

        webClientBuilder.build().post()
                .uri("http://inventory-service/api/v1/inventory/update-stock")
                .bodyValue(List.of(stockRequest))
                .retrieve()
                .bodyToMono(Void.class)
                .block();
        return this.mapToResponseDto(savedProduct);
    }

    @Override
    @Transactional
    public ResponseProductDto update(RequestProductDto requestProductDto, Long productId) {
            Product existingProduct = this.findProduct(productId);

            BigDecimal currentPrice = existingProduct.getPrice();
            BigDecimal newPrice = requestProductDto.getPrice();

            if(currentPrice.compareTo(newPrice) != 0){
                priceHistoryService.createPriceHistory(RequestPriceHistoryDto.builder()
                                .productId(productId)
                                .newPrice(newPrice)
                                .olderPrice(currentPrice)
                                .build());
            }

            existingProduct.setProductName(requestProductDto.getProductName());
            existingProduct.setDescription(requestProductDto.getDescription());
            existingProduct.setPrice(newPrice);
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

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id" + productId));

        return this.mapToResponseDto(product);
    }

    @Override
    public boolean addDiscountToProduct(Long productId, Long discountId) {
        Product product = findProduct(productId);
        Discount discount = discountService.getDiscount(discountId);

        product.setDiscount(discount);

        productRepo.save(product);
        return true;
    }

    @Override
    public OrderProductResponseDto getOrderProductDetails(Long productId) {

        LocalDateTime currentDateTime = LocalDateTime.now();
        Product product = productRepo.findDetailedProductWithActiveDiscount(productId, currentDateTime)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        BigDecimal originalPrice =  product.getPrice();
        BigDecimal discountedPrice = product.getPrice();
        boolean hasDiscount = false;

        if(product.getDiscount() != null){

            BigDecimal discountValue = product.getDiscount().getDiscountValue();

            if(product.getDiscount().getDiscountType() == DiscountType.FIXED){
                discountedPrice = originalPrice.subtract(discountValue);
            } else if (product.getDiscount().getDiscountType() == DiscountType.PERCENTAGE) {
                discountedPrice = originalPrice.multiply(discountValue.subtract(new BigDecimal(100)));
            }

            if(discountedPrice.compareTo(BigDecimal.ZERO) < 0){
                discountedPrice = BigDecimal.ZERO;
            }
            hasDiscount = true;
        }

        return OrderProductResponseDto.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .sku(product.getSkuCode())
                .price(product.getPrice())
                .hasDiscount(hasDiscount)
                .discountedPrice(discountedPrice)
                .build();
    }

    @Override
    public ProductShortDetails getDetailsForInventory(Long productId) {
        return productRepo.findShortDetailsById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }


    private Product findProduct(Long productId){
        return productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id" + productId));
    }



    private ResponseProductDto mapToResponseDto(Product product){
        return ResponseProductDto.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .price(product.getPrice())
                .skuCode(product.getSkuCode())
                .productImages(product.getImages().stream()
                        .map(img -> ResponseProductImageDto.builder()
                                .imageId(img.getId())
                                .base64Image(Base64.getEncoder().encodeToString(img.getImage()))
                                .createdAt(img.getCreatedAt())
                                .updatedAt(img.getUpdatedAt())
                                .build())
                        .collect(Collectors.toList()))
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
