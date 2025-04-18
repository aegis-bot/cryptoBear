package com.trade.cryptoBear.service;

import com.trade.cryptoBear.RequestObject.TradeRequest;
import com.trade.cryptoBear.exception.InsufficientBalanceException;
import com.trade.cryptoBear.exception.InsufficientQuantityException;

public interface BuySellService {
    public String performTrade(TradeRequest tradeRequest) throws InsufficientBalanceException, InsufficientQuantityException;
}
