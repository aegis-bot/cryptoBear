package com.trade.cryptoBear.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trade.cryptoBear.entity.Orderbook;

// Annotation
@Repository

// Interface extending CrudRepository
public interface OrderbookRepository
    extends CrudRepository<Orderbook, Long> {
        @Query(value = "SELECT * FROM ORDERBOOK o ORDER BY TIMESTAMP DESC LIMIT 2", nativeQuery = true)
        List<Orderbook> searchLatestOrders();

        @Query(value = "SELECT * FROM ORDERBOOK o WHERE currency= :symbol ORDER BY TIMESTAMP DESC LIMIT 1", nativeQuery = true)
        Orderbook searchLatestOrderOfSymbol(@Param("symbol") String symbol);
}
