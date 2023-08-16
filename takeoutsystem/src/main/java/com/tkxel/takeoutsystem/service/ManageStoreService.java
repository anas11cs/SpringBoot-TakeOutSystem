package com.tkxel.takeoutsystem.service;

import com.tkxel.takeoutsystem.dto.StoreDto;
import com.tkxel.takeoutsystem.entity.Store;

public interface ManageStoreService {
    Store createStore (Store store);
    StoreDto queryStore (Integer storeId);
    Store modifyStore (Store store);
    Boolean deleteStore(Integer storeId);
}
