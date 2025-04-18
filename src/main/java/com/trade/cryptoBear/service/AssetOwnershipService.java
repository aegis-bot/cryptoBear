package com.trade.cryptoBear.service;

import java.math.BigDecimal;

import com.trade.cryptoBear.entity.AssetOwnership;
import com.trade.cryptoBear.exception.InsufficientQuantityException;

public interface AssetOwnershipService {
    List<AssetOwnership> getTraderAssets(String username);
    AssetOwnership addAsset(String username, String symbol, BigDecimal qty);
    AssetOwnership removeAsset(String username, String symbol, BigDecimal qty) throws InsufficientQuantityException;
}
