package com.trade.cryptoBear.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.trade.cryptoBear.entity.AssetOwnership;
import com.trade.cryptoBear.exception.InsufficientQuantityException;
import com.trade.cryptoBear.service.AssetOwnershipService;

@Service
public class AssetOwnershipServiceImpl implements AssetOwnershipService {

    @Override
    public List<AssetOwnership> getTraderAssets(String username) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTraderAssets'");
    }

    @Override
    public AssetOwnership addAsset(String username, String symbol, BigDecimal qty) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addAsset'");
    }

    @Override
    public AssetOwnership removeAsset(String username, String symbol, BigDecimal qty)
            throws InsufficientQuantityException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAsset'");
    }
    

}
