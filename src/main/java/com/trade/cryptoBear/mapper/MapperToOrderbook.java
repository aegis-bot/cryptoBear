package com.trade.cryptoBear.mapper;

import java.time.LocalDateTime;

import com.trade.cryptoBear.entity.Orderbook;

public interface MapperToOrderbook {
    public Orderbook mapToOrderbook(LocalDateTime time);
    public String getSymbol();
}
