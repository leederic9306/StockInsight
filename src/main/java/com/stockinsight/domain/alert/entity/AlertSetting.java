package com.stockinsight.domain.alert.entity;

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
@Table(name = "alert_setting")
public class AlertSetting extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_code", nullable = false)
    private Stock stock;

    @Column(name = "target_price", precision = 15, scale = 2)
    private BigDecimal targetPrice;

    @Column(name = "variation_rate")
    private Float variationRate;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}