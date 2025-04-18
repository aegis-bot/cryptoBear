package com.trade.cryptoBear.service;

import com.trade.cryptoBear.RequestObject.TradeRequest;
import com.trade.cryptoBear.exception.InsufficientBalanceException;
import com.trade.cryptoBear.exception.InsufficientQuantityException;
import com.trade.cryptoBear.variables.TradeStatus;

public interface BuySellService {
    public TradeStatus performTrade(TradeRequest tradeRequest);
}
