package com.stockinsight.domain.stock.entity;

import com.stockinsight.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "stock")
public class Stock extends BaseTimeEntity {

    @Id
    @Column(name = "stock_code", length = 10)
    private String code;

    @Column(nullable = false, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Market market;
}