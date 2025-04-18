package com.trade.cryptoBear.service;

import java.util.List;

import com.trade.cryptoBear.entity.TradingHistory;

public class TradingHistoryService {
    TradingHistory addToHistory(TradingHistory history);
    List<TradingHistory> getAllHistory(String username);
}
