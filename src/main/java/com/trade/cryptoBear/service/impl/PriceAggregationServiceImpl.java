package com.trade.cryptoBear.service.impl;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


import com.trade.cryptoBear.ResponseObject.HuobiResponse;
import com.trade.cryptoBear.entity.Orderbook;
import com.trade.cryptoBear.mapper.MapperToOrderbook;
import com.trade.cryptoBear.repository.TraderAccountRepository;
import com.trade.cryptoBear.service.OrderbookService;
import com.trade.cryptoBear.upstreamObjects.BinanceObject;
import com.trade.cryptoBear.upstreamObjects.HuobiObject;


@Service
public class PriceAggregationServiceImpl {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final List<String> currenciesAvailable = List.of("ETHUSDT", "BTCUSDT");
    RestClient defaultClient = RestClient.create();
    List<MapperToOrderbook> allOrderbooks = new LinkedList<>();

    @Autowired
    private OrderbookService orderbookService;

    private String binance = "https://api.binance.com/api/v3/ticker/bookTicker";
    private String huobi = "https://api.huobi.pro/market/tickers";

	@Scheduled(fixedRate = 10000)
	private void retrieveBestPrice() {
        LocalDateTime currentTime = getCurrentTime();
        
        BinanceObject[] response1 = getBinanceObjects();
        HuobiObject[] response2 = getHuobiObjects();

        allOrderbooks.clear();
        for(String currency: currenciesAvailable) {
            Orderbook bestOrderbook = new Orderbook(currency, currentTime);
            Orderbook orderbook1 = findCurrencyFromList(response1, currency, currentTime);
            Orderbook orderbook2 = findCurrencyFromList(response2, currency, currentTime);
            //bid(sell) highest, ask(buy) lowest

            PriorityQueue<Orderbook> askQueue = new PriorityQueue<>(
                Comparator.comparing(Orderbook::getAskPrice)
            );
            PriorityQueue<Orderbook> bidQueue = new PriorityQueue<>(
                Comparator.comparing(Orderbook::getBidPrice).reversed()
            );
            
            askQueue.addAll(Arrays.asList(orderbook1, orderbook2));
            bidQueue.addAll(Arrays.asList(orderbook1, orderbook2));

            bestOrderbook.setAsk(askQueue.poll());
            bestOrderbook.setBid(bidQueue.poll());
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

    private BinanceObject[] getBinanceObjects() {
        return defaultClient.get().uri(binance).retrieve().toEntity(BinanceObject[].class).getBody();
    }

    private HuobiObject[] getHuobiObjects() {
        HuobiResponse huobiResp = defaultClient.get().uri(huobi).retrieve().toEntity(HuobiResponse.class).getBody();
        List<HuobiObject> listOfHuobiObjects = huobiResp.getData();
        return listOfHuobiObjects.toArray(new HuobiObject[listOfHuobiObjects.size()]);
    }
}
