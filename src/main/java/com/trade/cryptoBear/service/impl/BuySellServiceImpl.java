package com.trade.cryptoBear.service.impl;

import java.math.BigDecimal;
import java.util.Optional;

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
import com.trade.cryptoBear.service.AssetOwnershipService;
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
    @Autowired
    AssetOwnershipService assetOwnershipService;
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
            return sell(tradeRequest);
        } else {
            return TradeStatus.FAILED_UNKNOWN_ERROR;
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
            assetOwnershipService.addAsset(tradeRequest);
            return recordTradingHistory(tradeRequest, TradeStatus.SUCCESS);

        } else if (enoughBalance && !higherThanSell) {
            return recordTradingHistory(tradeRequest, TradeStatus.PENDING);
        } else if (enoughBalance && !sufficentOrderQty) {
            tradeRequest.setPricePerQty(orderPrice);
            tradeRequest.setQty(orderQty);
            BigDecimal toDeduct = tradeRequest.getPricePerQty().multiply(tradeRequest.getQty());
            traderAccountService.deductBalance(username, toDeduct);
            tradeRequest.setPricePerQty(pricePerQty);
            tradeRequest.setQty(qty);
            assetOwnershipService.addAsset(tradeRequest);
            return recordTradingHistory(tradeRequest, TradeStatus.PARTIALLY_FILLED);
        } else if (!enoughBalance) {
            return recordTradingHistory(tradeRequest, TradeStatus.FAILED_INSUFFICIENT_FUNDS);
        } else {
            return TradeStatus.FAILED_UNKNOWN_ERROR;
        }
    }

    TradeStatus sell(TradeRequest tradeRequest) {
        String username = tradeRequest.getUsername();
        String symbol = tradeRequest.getSymbol();
        BigDecimal pricePerQty = tradeRequest.getPricePerQty();
        BigDecimal qtyToSell = tradeRequest.getQty();
        if(Optional.ofNullable(assetOwnershipService.getTraderAssetsForSymbol(username, symbol)).isEmpty()) {
            return recordTradingHistory(tradeRequest, TradeStatus.FAILED_INSUFFICIENT_ASSETS);
        }
        BigDecimal qtyAvailable = assetOwnershipService.getTraderAssetsForSymbol(username, symbol).getQty();
        
        Orderbook orderbook = orderbookRepository.searchLatestOrderOfSymbol(symbol);
        BigDecimal orderPrice = orderbook.getBidPrice();
        BigDecimal orderQty = orderbook.getBidQty();

        boolean enoughAssets = qtyAvailable.compareTo(qtyToSell) >= 0;
        boolean lowerThanBuyPrice = pricePerQty.compareTo(orderPrice) <= 0;
        boolean sufficentOrderQty = qtyToSell.compareTo(orderQty) < 0;

        if (enoughAssets && lowerThanBuyPrice && sufficentOrderQty) {
            BigDecimal moneyToEarn = qtyToSell.multiply(pricePerQty);
            traderAccountService.addBalance(username, moneyToEarn);
            assetOwnershipService.removeAsset(tradeRequest);
            return recordTradingHistory(tradeRequest, TradeStatus.SUCCESS);
        } else if (enoughAssets && !lowerThanBuyPrice) {
            return recordTradingHistory(tradeRequest, TradeStatus.PENDING);
        } else if (enoughAssets && !sufficentOrderQty) {
            BigDecimal moneyToEarn = orderQty.multiply(pricePerQty);
            traderAccountService.addBalance(username, moneyToEarn);
            tradeRequest.setQty(orderQty);
            assetOwnershipService.removeAsset(tradeRequest);
            tradeRequest.setQty(qtyToSell);
            return recordTradingHistory(tradeRequest, TradeStatus.PARTIALLY_FILLED);
        } else if (!enoughAssets) {
            return recordTradingHistory(tradeRequest, TradeStatus.FAILED_INSUFFICIENT_ASSETS);
        } else {
            return TradeStatus.FAILED_UNKNOWN_ERROR;
        }


    }

    TradeStatus recordTradingHistory(TradeRequest tradeRequest, TradeStatus tradeStatus) {
        TradingHistory record = new TradingHistory(tradeRequest);
        record.setStatus(tradeStatus);
        tradingHistoryService.addToHistory(record);
        return tradeStatus;
    }
    
}
