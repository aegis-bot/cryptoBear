package com.trade.cryptoBear.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class TraderAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userid;
    
    @Column(unique=true)
    private String username;
    private BigDecimal balance;
}
