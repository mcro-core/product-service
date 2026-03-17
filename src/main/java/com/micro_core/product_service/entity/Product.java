package com.micro_core.product_service.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "product")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", nullable = false, length = 100)
    private String productName;

    @Column(name = "description", nullable = false, length = 250)
    private String description;

    @Column(name = "price", nullable = false, length = 100)
    private BigDecimal price;

    @Column(name = "sku_code", nullable = false)
    private String skuCode;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImages> images;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<PriceHistory> priceHistories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id", referencedColumnName = "id")
    private Discount discount;

}
