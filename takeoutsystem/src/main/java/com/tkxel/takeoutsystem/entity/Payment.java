package com.tkxel.takeoutsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private BigDecimal amountTendered;

    @OneToOne(mappedBy = "saleToPayment")
    private Sale paymentToSale;

    public Payment(BigDecimal amountTendered) {
        this.amountTendered = amountTendered;
    }

}