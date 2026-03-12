package com.micro_core.product_service.repo;

import com.micro_core.product_service.entity.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceHistoryRepo extends JpaRepository<PriceHistory, Long> {
}
