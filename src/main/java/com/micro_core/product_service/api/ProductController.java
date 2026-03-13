package com.micro_core.product_service.api;

import com.micro_core.product_service.dto.request.RequestProductDto;
import com.micro_core.product_service.dto.response.ResponseProductDto;
import com.micro_core.product_service.service.ProductService;
import jakarta.ws.rs.PUT;
import lombok.RequiredArgsConstructor;
import org.apache.http.protocol.HTTP;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseProductDto> createProduct(
            @RequestPart("product")RequestProductDto requestProductDto,
            @RequestPart("images")List<MultipartFile> images
            )throws IOException {
        ResponseProductDto responseProductDto = productService.create(requestProductDto, images);
        return new ResponseEntity<>(responseProductDto, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ResponseProductDto> updateProduct(
            @PathVariable Long productId,
            @RequestBody RequestProductDto requestProductDto
    ){
        ResponseProductDto responseUpdateProductDto = productService.update(requestProductDto, productId);

        return new ResponseEntity<>(responseUpdateProductDto, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId){

        productService.delete(productId);
        return ResponseEntity.ok("Product deleted successfully!");
    }

    @GetMapping
    public ResponseEntity <Page<ResponseProductDto>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
     ){
            return ResponseEntity.ok(productService.getAllProducts(page, size));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ResponseProductDto> getProductById(@PathVariable Long productId){

        ResponseProductDto responseProductDto = productService.getProductById(productId);
        return new ResponseEntity<>(responseProductDto, HttpStatus.OK);

    }

    @PostMapping("/discount")
    public ResponseEntity<String> addDiscountToProduct(Long productId, Long discountId){
        boolean response =  productService.addDiscountToProduct(productId, discountId);

        if(response){
            return ResponseEntity.ok("Discount successfully added!");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add discount. Please check Ids.");
        }
    }


}
