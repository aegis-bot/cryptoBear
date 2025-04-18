package com.trade.cryptoBear.controller;

import java.util.List;
// Importing required classes

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.trade.cryptoBear.RequestObject.TradeRequest;
import com.trade.cryptoBear.ResponseObject.AggregatedPriceResponse;
import com.trade.cryptoBear.entity.Department;
import com.trade.cryptoBear.entity.Orderbook;
import com.trade.cryptoBear.service.DepartmentService;
import com.trade.cryptoBear.service.OrderbookService;
import com.trade.cryptoBear.ResponseObject.TradeResponse;
import com.trade.cryptoBear.entity.AssetOwnership;
import com.trade.cryptoBear.exception.InsufficientBalanceException;
import com.trade.cryptoBear.exception.InsufficientQuantityException;
import com.trade.cryptoBear.service.AssetOwnershipService;
import com.trade.cryptoBear.service.BuySellService;
import com.trade.cryptoBear.service.TraderAccountService;
import com.trade.cryptoBear.service.TradingHistoryService;


// Annotation
@RestController

// Class
public class MainController {

    @Autowired 
    private OrderbookService orderbookService;

    @Autowired 
    private TraderAccountService traderAccountService;

    @Autowired
    private BuySellService buySellService;

    //@Autowired
    //private TradingHistoryService tradingHistoryService;
//
    //@Autowired
    //private AssetOwnershipService assetOwnershipService;

    // Read operation
    @GetMapping("/seeLatestPrice")
    public AggregatedPriceResponse getLatestPrices()
    {
        List<Orderbook> latestOrderbook = orderbookService.retrieveLatestOrderbook();
        return new AggregatedPriceResponse(latestOrderbook);
    }

    @PostMapping("/trade")
    public TradeResponse getMethodName(@Valid @RequestBody TradeRequest requestBody) {
        System.out.println();
        if (traderAccountService.verifyUser(requestBody.getUsername())) {
            try {
                buySellService.performTrade(requestBody);
            } catch (InsufficientBalanceException | InsufficientQuantityException ex) {

            }
        } else {

        }


        return new TradeResponse();
    }
    

}
