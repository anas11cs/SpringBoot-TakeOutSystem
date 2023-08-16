package com.tkxel.takeoutsystem.dto;

import com.tkxel.takeoutsystem.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
public class SaleDto {
    private Integer id;
    private LocalDate saleTime;
    private Boolean isComplete;
    private BigDecimal saleAmount;
    private Boolean isReadyToPay;
    private Boolean isAccept;
    private String saleName;
    private Set<OrderLineItem> saleToOrderLineItem;
    private Payment saleToPayment;
    private Store saleToStore;
    private Customer saleToCustomer;
    private Delivery saleToDelivery;
    public void saleToSaleDtoMapper(Sale sale){
        log.info("Mapping Sale for SaleId:" + sale.getId());
        this.id = sale.getId();
        this.saleTime  = sale.getSaleTime();
        this.isComplete = sale.getIsComplete();
        this.saleAmount = sale.getSaleAmount();
        this.isReadyToPay = sale.getIsReadyToPay();
        this.isAccept = sale.getIsAccept();
        this.saleName = sale.getSaleName();
    }
}
