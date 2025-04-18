package com.trade.cryptoBear.controller;

import java.util.List;
// Importing required classes

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.trade.cryptoBear.ResponseObject.AggregatedPriceResponse;
import com.trade.cryptoBear.entity.Department;
import com.trade.cryptoBear.entity.Orderbook;
import com.trade.cryptoBear.service.DepartmentService;
import com.trade.cryptoBear.service.OrderbookService;

// Annotation
@RestController

// Class
public class MainController {

    @Autowired 
    private OrderbookService orderbookService;

    // Read operation
    @GetMapping("/seeLatestPrice")
    public AggregatedPriceResponse getLatestPrices()
    {
        List<Orderbook> latestOrderbook = orderbookService.retrieveLatestOrderbook();
        return new AggregatedPriceResponse(latestOrderbook);
    }

}
