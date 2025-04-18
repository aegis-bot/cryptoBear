package com.trade.cryptoBear.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class AssetOwnership {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String assetId;
    private String userid;
    private String symbol;
    private BigDecimal qty;
}
