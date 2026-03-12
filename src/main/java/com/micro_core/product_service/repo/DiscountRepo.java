package com.micro_core.product_service.repo;

import com.micro_core.product_service.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepo extends JpaRepository<Discount, Long> {
}
