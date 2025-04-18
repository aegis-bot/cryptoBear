package com.trade.cryptoBear.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trade.cryptoBear.entity.Orderbook;
import com.trade.cryptoBear.repository.OrderbookRepository;
import com.trade.cryptoBear.service.OrderbookService;

@Service
public class OrderbookServiceImpl implements OrderbookService {
    
    @Autowired
    private OrderbookRepository orderbookRepo;
    
    @Override
    public Orderbook add(Orderbook orderbook) {
        return orderbookRepo.save(orderbook);
    }

    
    @Override
    public List<Orderbook> retrieveLatestOrderbook(){
        return (List<Orderbook>)orderbookRepo.searchLatestTransactions();
    }
}
