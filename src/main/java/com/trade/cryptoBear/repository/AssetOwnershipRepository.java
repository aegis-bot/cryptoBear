package com.trade.cryptoBear.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.trade.cryptoBear.entity.AssetOwnership;

import jakarta.transaction.Transactional;



@Repository
public interface AssetOwnershipRepository extends CrudRepository<AssetOwnership, Long>{

}
