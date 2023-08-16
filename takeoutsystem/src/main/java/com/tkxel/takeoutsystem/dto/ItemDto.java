package com.tkxel.takeoutsystem.dto;

import com.tkxel.takeoutsystem.entity.Item;
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
public class ItemDto {

    private Integer id;
    private String itemName;
    private BigDecimal itemPrice;
    private Integer stockNumber;
    private BigDecimal orderPrice;
    private Integer storeId;
    private String storeName;
    private String storeAddress;
    public void itemToItemDtoMapper(Item item){
        log.info("Mapping ItemDto for ItemId:" + item.getId());
        this.id = item.getId();
        this.itemName = item.getItemName();
        this.itemPrice = item.getItemPrice();
        this.stockNumber = item.getStockNumber();
        this.orderPrice = item.getOrderPrice();
        if(item.getItemToStore()!=null) {
           this.storeId = item.getItemToStore().getId();
           this.storeName = item.getItemToStore().getStoreName();
           this.storeAddress = item.getItemToStore().getStoreAddress();
        }
    }
}
