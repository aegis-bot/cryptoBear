package com.trade.cryptoBear.RequestObject;

import com.trade.cryptoBear.variables.TradeType;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.RequestMapping;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeRequest {
    @NotBlank
    private String option;
    @NotBlank
    private String symbol;
    @NotBlank
    private BigDecimal pricePerQty;
    @NotBlank
    private BigDecimal qty;
    @NotBlank
    private String username;  // obviously security flaw, use this in place of cookies
}


