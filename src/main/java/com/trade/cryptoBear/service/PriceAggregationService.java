package com.trade.cryptoBear.service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.w3c.dom.NameList;
import org.springframework.http.ResponseEntity;

import com.trade.cryptoBear.ResponseObject.HuobiResponse;
import com.trade.cryptoBear.entity.Orderbook;
import com.trade.cryptoBear.mapper.MapperToOrderbook;
import com.trade.cryptoBear.repository.OrderbookRepository;
import com.trade.cryptoBear.upstreamObjects.BinanceObject;
import com.trade.cryptoBear.upstreamObjects.HuobiObject;

import reactor.core.publisher.Mono;

@Service
public class PriceAggregationService {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final List<String> currenciesAvailable = List.of("ETHUSDT", "BTCUSDT");
    RestClient defaultClient = RestClient.create();
    List<MapperToOrderbook> allOrderbooks = new LinkedList<>();

    @Autowired
    private OrderbookService orderbookService;
    
    private String binance = "https://api.binance.com/api/v3/ticker/bookTicker";
    private String huobi = "https://api.huobi.pro/market/tickers";

	@Scheduled(fixedRate = 10000)
	public void retrieveBestPrice() {
        LocalDateTime currentTime = getCurrentTime();
        
        BinanceObject[] response1 = defaultClient.get().uri(binance).retrieve().toEntity(BinanceObject[].class).getBody();

        HuobiResponse huobiResp = defaultClient.get().uri(huobi).retrieve().toEntity(HuobiResponse.class).getBody();
        List<HuobiObject> listOfHuobiObjects = huobiResp.getData();
        HuobiObject[] response2 = listOfHuobiObjects.toArray(new HuobiObject[listOfHuobiObjects.size()]);

        allOrderbooks.clear();
        for(String currency: currenciesAvailable) {
            Orderbook bestOrderbook = new Orderbook(currency, currentTime);
            Orderbook orderbook1 = findCurrencyFromList(response1, currency, currentTime);
            Orderbook orderbook2 = findCurrencyFromList(response2, currency, currentTime);
            //bid(sell) highest, ask(buy) lowest
            if(orderbook1.getBidPrice().compareTo(orderbook2.getBidPrice()) > 0) {
                bestOrderbook.setBid(orderbook1);
            } else if (orderbook1.getBidPrice().compareTo(orderbook2.getBidPrice()) < 0) {
                bestOrderbook.setBid(orderbook2);
            } else if (orderbook1.getBidQty().compareTo(orderbook2.getBidQty()) > 0) {
                bestOrderbook.setBid(orderbook1);
            } else {
                bestOrderbook.setBid(orderbook2);
            }
            if(orderbook1.getAskPrice().compareTo(orderbook2.getAskPrice()) < 0) {
                bestOrderbook.setAsk(orderbook1);
            } else if (orderbook1.getAskPrice().compareTo(orderbook2.getAskPrice()) > 0) {
                bestOrderbook.setAsk(orderbook2);
            } else if (orderbook1.getAskPrice().compareTo(orderbook2.getAskPrice()) > 0) {
                bestOrderbook.setAsk(orderbook1);
            } else {
                bestOrderbook.setAsk(orderbook2);
            }
            orderbookService.add(bestOrderbook);
        }
	}

    private Orderbook findCurrencyFromList(MapperToOrderbook[] object, String currencyName, LocalDateTime time) {
        for (MapperToOrderbook element: object) {
            if(element.getSymbol().equalsIgnoreCase(currencyName)) {
                return element.mapToOrderbook(time);
            }
        }
        return null;
    }

    private LocalDateTime getCurrentTime() {
        LocalDateTime currentDateTime = LocalDateTime.now();
		return currentDateTime;
	}
}
