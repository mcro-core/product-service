package com.micro_core.product_service.repo;

import com.micro_core.product_service.entity.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepo extends JpaRepository<ProductImages, Long> {
}
