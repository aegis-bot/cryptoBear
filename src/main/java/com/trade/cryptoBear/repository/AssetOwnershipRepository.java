package com.trade.cryptoBear.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trade.cryptoBear.entity.AssetOwnership;

import jakarta.transaction.Transactional;



@Repository
public interface AssetOwnershipRepository extends CrudRepository<AssetOwnership, Long>{
    
    @Query(value = "SELECT * FROM Asset_Ownership WHERE userid= :username", nativeQuery = true)
    List<AssetOwnership> getAllUserAssets(@Param("username") String username);

    @Query(value = "SELECT * FROM Asset_Ownership WHERE userid= :username AND symbol =:symbol", nativeQuery = true)
    AssetOwnership getUserAsset(@Param("username") String username, @Param("symbol") String symbol);

    @Modifying
    @Transactional
    @Query("UPDATE AssetOwnership SET qty= :qty WHERE userid= :username AND symbol= :symbol")
    void updateAssetOwnership(@Param("username") String username, @Param("symbol") String symbol, @Param("qty") BigDecimal qty);
}
