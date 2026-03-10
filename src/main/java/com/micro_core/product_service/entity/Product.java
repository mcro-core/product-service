package com.micro_core.product_service.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;

    @Column(name = "description", nullable = false, length = 250)
    private String description;

    @Column(name = "price", nullable = false, length = 100)
    private BigDecimal price;

    @Column(name = "sku_code", nullable = false)
    private String skuCode;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;
}
