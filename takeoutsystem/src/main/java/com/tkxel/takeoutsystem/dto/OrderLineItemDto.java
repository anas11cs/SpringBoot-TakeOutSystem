package com.tkxel.takeoutsystem.dto;

import com.tkxel.takeoutsystem.entity.Item;
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
public class OrderLineItemDto {
    private Integer id;
    private Integer quantity;
    private BigDecimal subAmount;
    private Item orderLineItemtoItem;
    private Sale orderLineItemtoSale;
}
