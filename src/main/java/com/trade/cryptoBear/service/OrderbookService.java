package com.trade.cryptoBear.service;

import java.util.List;

import com.trade.cryptoBear.entity.Orderbook;

public interface OrderbookService {
    Orderbook add(Orderbook orderbook);
    List<Orderbook> retrieveLatestOrderbook();
}
