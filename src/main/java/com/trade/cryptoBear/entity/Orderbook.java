package com.trade.cryptoBear.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.trade.cryptoBear.upstreamObjects.BinanceObject;
import com.trade.cryptoBear.upstreamObjects.HuobiObject;

// Importing required classes (using jakarta.persistence now)

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@Getter
@Setter
// Class
public class Orderbook {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String currency;
    private BigDecimal bidPrice;    //SELL
    private BigDecimal bidQty;
    private BigDecimal askPrice;    //BUY
    private BigDecimal askQty;
    private LocalDateTime timestamp;

    public Orderbook(String currency, LocalDateTime time){
        this.setCurrency(currency);
        this.setTimestamp(time);
    }

    public void setBid(Orderbook orderbook) {
        this.setBidPrice(orderbook.getBidPrice());
        this.setBidQty(orderbook.getBidQty());
    }

    public void setAsk(Orderbook orderbook) {
        this.setAskPrice(orderbook.getAskPrice());
        this.setAskQty(orderbook.getAskQty());
    }
}