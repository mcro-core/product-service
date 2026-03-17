package com.micro_core.product_service.api;

import com.micro_core.product_service.dto.request.RequestProductDto;
import com.micro_core.product_service.dto.response.OrderProductResponseDto;
import com.micro_core.product_service.dto.response.ProductShortDetails;
import com.micro_core.product_service.dto.response.ResponseProductDto;
import com.micro_core.product_service.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.PathParam;
import lombok.RequiredArgsConstructor;
import org.apache.http.protocol.HTTP;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Validated

public class ProductController {
    private final ProductService productService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseProductDto> createProduct(
            @Valid @RequestPart("product") RequestProductDto requestProductDto,
            @NotEmpty(message = "At least one product image is required")
            @RequestPart("images") List<MultipartFile> images
    ) throws IOException {
        ResponseProductDto responseProductDto = productService.create(requestProductDto, images);
        return new ResponseEntity<>(responseProductDto, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ResponseProductDto> updateProduct(
            @Min(value = 1, message = "Invalid Product ID") @PathVariable Long productId,
            @Valid @RequestBody RequestProductDto requestProductDto
    ) {
        ResponseProductDto responseUpdateProductDto = productService.update(requestProductDto, productId);
        return new ResponseEntity<>(responseUpdateProductDto, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(
            @Min(value = 1, message = "Invalid Product ID") @PathVariable Long productId) {
        productService.delete(productId);
        return ResponseEntity.ok("Product deleted successfully!");
    }

    @GetMapping
    public ResponseEntity<Page<ResponseProductDto>> getAllProducts(
            @Min(value = 0, message = "Page index cannot be negative")
            @RequestParam(defaultValue = "0") int page,

            @Min(value = 1, message = "Page size must be at least 1")
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(productService.getAllProducts(page, size));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ResponseProductDto> getProductById(
            @Min(value = 1, message = "Invalid Product ID")
            @PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @PostMapping("/discount")
    public ResponseEntity<String> addDiscountToProduct(
            @NotNull(message = "Product ID is required") @RequestParam Long productId,
            @NotNull(message = "Discount ID is required") @RequestParam Long discountId) {

        boolean response = productService.addDiscountToProduct(productId, discountId);

        if (response) {
            return ResponseEntity.ok("Discount successfully added!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to add discount. Please check IDs.");
        }
    }

    @GetMapping("/order-product/{productId}")
    public ResponseEntity<OrderProductResponseDto> getOrderProductDetail(
            @Min(value = 1, message = "Invalid Product ID")
            @PathVariable Long productId) {
        return ResponseEntity.ok(productService.getOrderProductDetails(productId));
    }

    @GetMapping("/product-inventory/{productId}")
    public ResponseEntity<ProductShortDetails> getDetailsForInventory(
            @Min(value = 1, message = "Invalid Product ID")
            @PathVariable Long productId) {
        return ResponseEntity.ok(productService.getDetailsForInventory(productId));
    }

}
