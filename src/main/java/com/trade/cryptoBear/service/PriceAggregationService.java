package com.trade.cryptoBear.service;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;


import com.trade.cryptoBear.ResponseObject.HuobiResponse;
import com.trade.cryptoBear.entity.Orderbook;
import com.trade.cryptoBear.mapper.MapperToOrderbook;
import com.trade.cryptoBear.repository.TraderAccountRepository;
import com.trade.cryptoBear.repository.UserRepository;
import com.trade.cryptoBear.upstreamObjects.BinanceObject;
import com.trade.cryptoBear.upstreamObjects.HuobiObject;


@Service
public class PriceAggregationService {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final List<String> currenciesAvailable = List.of("ETHUSDT", "BTCUSDT");
    RestClient defaultClient = RestClient.create();
    List<MapperToOrderbook> allOrderbooks = new LinkedList<>();

    @Autowired
    private OrderbookService orderbookService;
    
    @Autowired
    TraderAccountRepository traderRepository;

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
