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
public class BinanceObject implements MapperToOrderbook {
    private String symbol;
    private BigDecimal bidPrice;  //to sell
    private BigDecimal bidQty;
    private BigDecimal askPrice; // to buy
    private BigDecimal askQty;

    @Override
    public Orderbook mapToOrderbook(LocalDateTime time) {
        Orderbook orderbook = new Orderbook();
        orderbook.setCurrency(this.getSymbol());
        orderbook.setBidPrice(this.getBidPrice());
        orderbook.setBidQty(this.getBidQty());
        orderbook.setAskPrice(this.getBidPrice());
        orderbook.setAskQty(this.getAskQty());
        orderbook.setTimestamp(time);
        return orderbook;
    }
}
