package com.trade.cryptoBear.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trade.cryptoBear.entity.TradingHistory;
import com.trade.cryptoBear.repository.TradingHistoryRepository;
import com.trade.cryptoBear.service.TradingHistoryService;

@Service
public class TradingHistoryServiceImpl implements  TradingHistoryService {

    @Autowired
    TradingHistoryRepository tradingHistoryRepository;

    @Override
    public TradingHistory addToHistory(TradingHistory history) {
        return tradingHistoryRepository.save(history);
    }

    @Override
    public List<TradingHistory> getAllHistory(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllHistory'");
    }

}
