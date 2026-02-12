package com.stockinsight.domain.portfolio.entity;

import com.stockinsight.domain.stock.entity.Stock;
import com.stockinsight.domain.user.entity.User;
import com.stockinsight.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "portfolio")
public class Portfolio extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_code", nullable = false)
    private Stock stock;

    @Column(name = "avg_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal avgPrice;

    @Column(nullable = false)
    private Integer quantity;
}