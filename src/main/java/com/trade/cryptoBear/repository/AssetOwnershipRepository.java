package com.trade.cryptoBear.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.trade.cryptoBear.entity.AssetOwnership;



@Repository
public interface AssetOwnershipRepository extends CrudRepository<AssetOwnership, Long>{
    
}
