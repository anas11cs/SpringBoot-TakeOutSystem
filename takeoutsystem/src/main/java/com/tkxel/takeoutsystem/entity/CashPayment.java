package com.tkxel.takeoutsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CashPayment extends Payment{
    private BigDecimal balance;
    public CashPayment(BigDecimal amountTendered, BigDecimal balance) {
        super(amountTendered);
        this.balance = balance;
    }
}
