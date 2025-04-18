package com.trade.cryptoBear.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trade.cryptoBear.entity.AssetOwnership;
import com.trade.cryptoBear.entity.TraderAccount;



@Repository
public interface TraderAccountRepository extends CrudRepository<TraderAccount, Long> {
    @Query(value = "SELECT username FROM TraderAccount t WHERE username= :username")
    TraderAccount getTradername(@Param("username") String username);

    @Query(value = "SELECT balance FROM TraderAccount t WHERE username= :username")
    BigDecimal getBalance(@Param("username") String username);
}
