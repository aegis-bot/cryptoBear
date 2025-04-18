package com.trade.cryptoBear.service;

import java.util.List;

import com.trade.cryptoBear.entity.TradingHistory;

public interface TradingHistoryService {
    public TradingHistory addToHistory(TradingHistory history);
    public List<TradingHistory> getAllHistory(String username);
}
