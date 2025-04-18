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
    TradingHistoryRepository historyRepo;

    @Override
    public TradingHistory addToHistory(TradingHistory history) {
        return historyRepo.save(history);
    }

    @Override
    public List<TradingHistory> getAllHistory(String username) {
        return historyRepo.getTradingHistory(username);
    }

}
