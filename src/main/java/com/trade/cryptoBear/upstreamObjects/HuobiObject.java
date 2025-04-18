package com.trade.cryptoBear.upstreamObjects;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.trade.cryptoBear.entity.Orderbook;
import com.trade.cryptoBear.mapper.MapperToOrderbook;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class HuobiObject implements  MapperToOrderbook {
    private String symbol;
    private BigDecimal bid;  //to sell
    private BigDecimal bidSize;
    private BigDecimal ask; // to buy
    private BigDecimal askSize;

    @Override
    public Orderbook mapToOrderbook(LocalDateTime time) {
        Orderbook orderbook = new Orderbook();
        orderbook.setCurrency(this.getSymbol().toUpperCase());
        orderbook.setBidPrice(this.getBid());
        orderbook.setBidQty(this.getBidSize());
        orderbook.setAskPrice(this.getAsk());
        orderbook.setAskQty(this.getAskSize());
        orderbook.setTimestamp(time);
        return orderbook;
    }
}
