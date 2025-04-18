package com.trade.cryptoBear.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.trade.cryptoBear.RequestObject.TradeRequest;
import com.trade.cryptoBear.variables.TradeStatus;
import com.trade.cryptoBear.variables.TradeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TradingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userid;
    private String tradeType;
    private String symbol;
    private BigDecimal pricePerQty;
    private BigDecimal qty;
    private TradeStatus status;
    private BigDecimal totalAmount;

    @Column(nullable = false)
    private LocalDateTime createdDt;
    private LocalDateTime lastUpdatedDt;

    public TradingHistory(TradeRequest tradeRequest) {
        this.setUserid(tradeRequest.getUsername());
        this.setTradeType(tradeRequest.getOption().toUpperCase());
        this.setSymbol(tradeRequest.getSymbol());
        this.setPricePerQty(tradeRequest.getPricePerQty());
        this.setQty(tradeRequest.getQty());
        this.setTotalAmount(this.getPricePerQty().multiply(this.getQty()));
        this.setCreatedDt(LocalDateTime.now());
        this.setLastUpdatedDt(this.getCreatedDt());
    }

}
