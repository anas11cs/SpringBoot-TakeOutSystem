package com.tkxel.takeoutsystem.service;
import com.tkxel.takeoutsystem.entity.Sale;
import java.math.BigDecimal;
import java.time.LocalDate;

public interface ProcessOrderService {
    Boolean makeNewOrder(Integer storeId , Sale saleObject);
    Boolean enterItem(Integer saleId, Integer itemId , Integer itemQuantity) ;
    BigDecimal endOrder(Integer saleId);
    Boolean makeCashPayment(BigDecimal payableAmount, Integer saleId);
    Boolean makeCardPayment(BigDecimal payableAmount, String cardAccountNumber, LocalDate expireDate , Integer saleId);
}
