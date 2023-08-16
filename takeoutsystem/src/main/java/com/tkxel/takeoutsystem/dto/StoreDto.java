package com.tkxel.takeoutsystem.dto;

import com.tkxel.takeoutsystem.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
public class StoreDto {
    private Integer id;
    private String storeName;
    private String storeAddress;
    private Boolean isOpened;
    public void storeToStoreDtoMapper(Store store){
        log.info("Mapping Store for StoreId:" + store.getId());
        this.id = store.getId();
        this.storeName = store.getStoreName();
        this.storeAddress = store.getStoreAddress();
        this.isOpened = store.getIsOpened();
    }
}
