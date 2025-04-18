package com.trade.cryptoBear.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trade.cryptoBear.RequestObject.TradeRequest;
import com.trade.cryptoBear.entity.AssetOwnership;
import com.trade.cryptoBear.entity.Orderbook;
import com.trade.cryptoBear.entity.TraderAccount;
import com.trade.cryptoBear.entity.TradingHistory;
import com.trade.cryptoBear.exception.InsufficientBalanceException;
import com.trade.cryptoBear.exception.InsufficientQuantityException;
import com.trade.cryptoBear.repository.OrderbookRepository;
import com.trade.cryptoBear.repository.TraderAccountRepository;
import com.trade.cryptoBear.service.BuySellService;
import com.trade.cryptoBear.service.OrderbookService;
import com.trade.cryptoBear.service.TraderAccountService;
import com.trade.cryptoBear.service.TradingHistoryService;
import com.trade.cryptoBear.variables.TradeStatus;

@Service
public class BuySellServiceImpl implements BuySellService {

    @Autowired
    OrderbookRepository orderbookRepository;
//
    @Autowired
    TraderAccountService traderAccountService;
//
    //@Autowired
    //AssetOwnershipService assetOwnershipService;
//
    @Autowired
    TradingHistoryService tradingHistoryService;



    @Override
    public TradeStatus performTrade(TradeRequest tradeRequest) {
        String option = tradeRequest.getOption();
        
        /* 
        check if buy or sell
        if buy,
            check account balance and orderbookService ask
        if sell,
            check ownership and orderbookService bid

        */

        if(option.equalsIgnoreCase("BUY")) {
            return buy(tradeRequest);
        } else if (option.equalsIgnoreCase("SELL")) {
            return TradeStatus.SUCCESS;
        } else {
            return TradeStatus.UNKNOWN_ERROR;
        }
    }

    TradeStatus buy(TradeRequest tradeRequest) {
        String username = tradeRequest.getUsername();
        String symbol = tradeRequest.getSymbol();
        BigDecimal pricePerQty = tradeRequest.getPricePerQty();
        BigDecimal qty = tradeRequest.getQty();

        BigDecimal balance = traderAccountService.getBalance(tradeRequest.getUsername());
        BigDecimal totalAmount = pricePerQty.multiply(qty);

        Orderbook orderbook = orderbookRepository.searchLatestOrderOfSymbol(symbol);
        BigDecimal orderPrice = orderbook.getAskPrice();
        BigDecimal orderQty = orderbook.getAskQty();

        boolean enoughBalance = balance.compareTo(totalAmount) >= 0;
        boolean higherThanSell = pricePerQty.compareTo(orderPrice) >= 0;
        boolean sufficentOrderQty = qty.compareTo(orderQty) < 0;
    
        
        if (enoughBalance && higherThanSell && sufficentOrderQty) {
            tradeRequest.setPricePerQty(orderPrice);
            BigDecimal toDeduct = tradeRequest.getPricePerQty().multiply(tradeRequest.getQty());
            traderAccountService.deductBalance(username, toDeduct);
            // add AssetOwnership logic
            TradingHistory record = new TradingHistory(tradeRequest);
            record.setStatus(TradeStatus.SUCCESS);
            tradingHistoryService.addToHistory(record);
            return TradeStatus.SUCCESS;

        } else if (enoughBalance && !higherThanSell) {
            TradingHistory record = new TradingHistory(tradeRequest);
            record.setStatus(TradeStatus.PENDING);
            tradingHistoryService.addToHistory(record);
            return TradeStatus.PENDING;
        } else if (enoughBalance && higherThanSell && !sufficentOrderQty) {
            tradeRequest.setPricePerQty(orderPrice);
            tradeRequest.setQty(orderQty);
            BigDecimal toDeduct = tradeRequest.getPricePerQty().multiply(tradeRequest.getQty());
            traderAccountService.deductBalance(username, toDeduct);
            //add AssetOwnership logic 

            TradingHistory record = new TradingHistory(tradeRequest);
            record.setStatus(TradeStatus.PARTIALLY_FILLED);
            tradingHistoryService.addToHistory(record);
            return TradeStatus.PARTIALLY_FILLED;

        } else if (!enoughBalance) {
            TradingHistory record = new TradingHistory(tradeRequest);
            record.setStatus(TradeStatus.INSUFFICIENT_FUNDS);
            return TradeStatus.INSUFFICIENT_FUNDS;
        } else {
            return TradeStatus.UNKNOWN_ERROR;
        }
    }
    
}
