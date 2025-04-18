package com.trade.cryptoBear.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trade.cryptoBear.entity.AssetOwnership;
import com.trade.cryptoBear.entity.TradingHistory;



@Repository
public interface TradingHistoryRepository extends CrudRepository<TradingHistory, Long>{
    
}
