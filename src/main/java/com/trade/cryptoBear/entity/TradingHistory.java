package com.trade.cryptoBear.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.trade.cryptoBear.variables.TradeStatus;
import com.trade.cryptoBear.variables.TradeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TradingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userid;
    private TradeType tradeType;
    private String symbol;
    private BigDecimal amount;
    private BigDecimal qty;
    private TradeStatus status;

    @Column(nullable = false)
    private LocalDateTime createdDt;
    private LocalDateTime lastUpdatedDt;
}
