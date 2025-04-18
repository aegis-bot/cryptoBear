package com.trade.cryptoBear.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trade.cryptoBear.entity.AssetOwnership;
import com.trade.cryptoBear.entity.TradingHistory;



@Repository
public interface TradingHistoryRepository extends CrudRepository<TradingHistory, Long>{
    @Query(value = "SELECT * FROM trading_History WHERE userid= :username", nativeQuery=true)
    List<TradingHistory> getTradingHistory (@Param("username") String username);
}
