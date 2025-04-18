package com.trade.cryptoBear.ResponseObject;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.ResponseBody;

import com.trade.cryptoBear.entity.Orderbook;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@ResponseBody
@Getter
@Setter
public class AggregatedPriceResponse {
    List<OrderbookResponse> data;

    public AggregatedPriceResponse(List<Orderbook> orderbooks) {
        this.data = orderbooks.stream().map((obj) -> {
            return new OrderbookResponse(
                obj.getCurrency(),
                obj.getBidPrice(),
                obj.getBidQty(),
                obj.getAskPrice(),
                obj.getAskQty()
            );
        }).collect(Collectors.toList());
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class OrderbookResponse {
        private String currency;
        private BigDecimal bidPrice;    //SELL
        private BigDecimal bidQty;
        private BigDecimal askPrice;    //BUY
        private BigDecimal askQty;
    }
}
