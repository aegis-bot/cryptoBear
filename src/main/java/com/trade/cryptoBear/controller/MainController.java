package com.trade.cryptoBear.controller;

import java.util.List;
// Importing required classes

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.trade.cryptoBear.RequestObject.TradeRequest;
import com.trade.cryptoBear.ResponseObject.AggregatedPriceResponse;
import com.trade.cryptoBear.entity.Orderbook;
import com.trade.cryptoBear.service.OrderbookService;
import com.trade.cryptoBear.service.BuySellService;
import com.trade.cryptoBear.service.TraderAccountService;
import com.trade.cryptoBear.service.TradingHistoryService;
import com.trade.cryptoBear.variables.TradeStatus;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.trade.cryptoBear.RequestObject.IdentityRequest;
import com.trade.cryptoBear.entity.TradingHistory;



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

    @Autowired
    private TradingHistoryService tradingHistoryService;
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
    public ResponseEntity<String> getMethodName(@Valid @RequestBody TradeRequest requestBody) {
        if (traderAccountService.verifyUser(requestBody.getUsername())) {
            TradeStatus status =  buySellService.performTrade(requestBody);
            return new ResponseEntity<>(status.toString(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Forbidden", HttpStatus.FORBIDDEN);
        } 
    }

    @PostMapping("/checkBalanceAndAssets")
    public String checkBalance(@Valid @RequestBody IdentityRequest requestBody) {
        //TODO: process POST request
        
        return entity;
    }
    

    @PostMapping("/seeTradingHistory")
    public List<TradingHistory> fetchTradingHistory(@Valid @RequestBody IdentityRequest requestBody) {
        List<TradingHistory> allRecords = tradingHistoryService.getAllHistory(requestBody.getUsername());
        return allRecords;
    }
    
    

}
