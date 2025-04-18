package com.trade.cryptoBear.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trade.cryptoBear.exception.InsufficientBalanceException;
import com.trade.cryptoBear.repository.TraderAccountRepository;
import com.trade.cryptoBear.service.TraderAccountService;

@Service
public class TraderAccountServiceImpl implements TraderAccountService {

    @Autowired
    TraderAccountRepository traderRepository;

    @Override
    public boolean verifyUser(String username) {
        if (username.equals(traderRepository.getTradername(username))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public BigDecimal getBalance(String username) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BigDecimal addBalance(String username, BigDecimal amount) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BigDecimal deductBalance(String username, BigDecimal amount) throws InsufficientBalanceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
}
