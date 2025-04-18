package com.trade.cryptoBear.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trade.cryptoBear.RequestObject.TradeRequest;
import com.trade.cryptoBear.entity.AssetOwnership;
import com.trade.cryptoBear.exception.InsufficientQuantityException;
import com.trade.cryptoBear.repository.AssetOwnershipRepository;
import com.trade.cryptoBear.service.AssetOwnershipService;

@Service
public class AssetOwnershipServiceImpl implements AssetOwnershipService {

    @Autowired
    AssetOwnershipRepository assetOwnershipRepository;

    @Override
    public List<AssetOwnership> getTraderAssets(String username) {
       return assetOwnershipRepository.getAllUserAssets(username);
    }

    @Override
    public AssetOwnership getTraderAssetsForSymbol(String username, String symbol) {
        return assetOwnershipRepository.getUserAsset(username, symbol);
    }

    @Override
    public AssetOwnership addAsset(TradeRequest tradeRequest) {
        String username = tradeRequest.getUsername();
        String symbol = tradeRequest.getSymbol();
        BigDecimal qtyToAdd = tradeRequest.getQty();
        Optional<AssetOwnership> currentAssetOwnership = Optional.ofNullable(assetOwnershipRepository.getUserAsset(username, symbol));
        BigDecimal currentQty;
        if (currentAssetOwnership.isEmpty()) {
            currentQty = new BigDecimal(0);
            return saveAssetOwnership(username, symbol, qtyToAdd);
        } else {
            currentQty = currentAssetOwnership.get().getQty();
        }
        BigDecimal sum = currentQty.add(qtyToAdd);
        return updateAssetOwnership(username, symbol, sum);
    }

    @Override
    public AssetOwnership removeAsset(TradeRequest tradeRequest) {
        String username = tradeRequest.getUsername();
        String symbol = tradeRequest.getSymbol();
        BigDecimal qtyToAdd = tradeRequest.getQty();
        BigDecimal currentQty = assetOwnershipRepository.getUserAsset(username, symbol).getQty();
        BigDecimal difference = currentQty.subtract(qtyToAdd);
        return updateAssetOwnership(username, symbol, difference);
    }

    private AssetOwnership updateAssetOwnership(String username, String symbol, BigDecimal qty) {
        AssetOwnership updatedAssetOwnership = new AssetOwnership();
        updatedAssetOwnership.setUserid(username);
        updatedAssetOwnership.setSymbol(symbol);
        updatedAssetOwnership.setQty(qty);
        assetOwnershipRepository.updateAssetOwnership(username, symbol, qty);
        return assetOwnershipRepository.getUserAsset(username, symbol);
    }

    private AssetOwnership saveAssetOwnership(String username, String symbol, BigDecimal qty) {
        AssetOwnership newAssetOwnership = new AssetOwnership();
        newAssetOwnership.setUserid(username);
        newAssetOwnership.setSymbol(symbol);
        newAssetOwnership.setQty(qty);
        assetOwnershipRepository.save(newAssetOwnership);
        return assetOwnershipRepository.getUserAsset(username, symbol);
    }
    

}
