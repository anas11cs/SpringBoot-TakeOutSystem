package com.tkxel.takeoutsystem.dto;

import com.tkxel.takeoutsystem.entity.Sale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
public class PaymentDto {
    private Integer id;
    private BigDecimal amountTendered;
    private Sale paymentToSale;
}
