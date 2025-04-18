package com.trade.cryptoBear.service;

import java.math.BigDecimal;

import com.trade.cryptoBear.RequestObject.TradeRequest;
import com.trade.cryptoBear.exception.InsufficientBalanceException;

public interface TraderAccountService {
    boolean verifyUser(String username);
    BigDecimal getBalance(String username);

    //return final balance
    BigDecimal addBalance(String username, BigDecimal amount);
    BigDecimal deductBalance(String username, BigDecimal amount);
}
