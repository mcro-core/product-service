package com.micro_core.product_service.repo;

import com.micro_core.product_service.dto.response.ProductShortDetails;
import com.micro_core.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p " +
            "LEFT JOIN FETCH p.discount d " +
            "WHERE p.id = :productId " +
            "AND (d IS NULL OR (d.isActive = true AND :currentDate BETWEEN d.startDate AND d.endDate))")
    Optional<Product> findDetailedProductWithActiveDiscount(
            @Param("productId") Long productId,
            @Param("currentDate") LocalDateTime currentDate
    );

    @Query("SELECT p.productName as productName, p.skuCode as skuCode FROM Product p WHERE p.id = :productId")
    Optional<ProductShortDetails> findShortDetailsById(@Param("productId") Long productId);
}
