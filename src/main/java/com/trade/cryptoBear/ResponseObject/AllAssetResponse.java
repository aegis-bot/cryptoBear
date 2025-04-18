package com.trade.cryptoBear.ResponseObject;

import java.math.BigDecimal;
import java.util.List;

import com.trade.cryptoBear.entity.AssetOwnership;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AllAssetResponse {
    private BigDecimal balance;
    private List<AssetOwnership> allAssets;
}
