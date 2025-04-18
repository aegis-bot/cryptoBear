package com.trade.cryptoBear.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.trade.cryptoBear.RequestObject.TradeRequest;
import com.trade.cryptoBear.entity.AssetOwnership;
import com.trade.cryptoBear.entity.TraderAccount;
import com.trade.cryptoBear.entity.TradingHistory;
import com.trade.cryptoBear.exception.InsufficientBalanceException;
import com.trade.cryptoBear.exception.InsufficientQuantityException;
import com.trade.cryptoBear.repository.OrderbookRepository;
import com.trade.cryptoBear.service.AssetOwnershipService;
import com.trade.cryptoBear.service.BuySellService;
import com.trade.cryptoBear.service.OrderbookService;
import com.trade.cryptoBear.service.TraderAccountService;
import com.trade.cryptoBear.service.TradingHistoryService;

@Service
public class BuySellServiceImpl implements BuySellService {


    //@Autowired
    //OrderbookService orderbookService;
//
    //@Autowired
    //TraderAccountService traderAccountService;
//
    //@Autowired
    //AssetOwnershipService assetOwnershipService;
//
    //@Autowired
    //TradingHistoryService tradingHistoryService;



    @Override
    String performTrade(TradeRequest tradeRequest) throws InsufficientBalanceException, InsufficientQuantityException {
        
        /* 
        check if buy or sell
        if buy,
            check account balance and orderbookService ask
        if sell,
            check ownership and orderbookService bid

        */

        return "";
    }
    
}
