package com.tkxel.takeoutsystem.service;

import com.tkxel.takeoutsystem.entity.Item;
import com.tkxel.takeoutsystem.entity.Sale;
import java.util.Set;

public interface TakeOutSystem {

    Item search(String itemName);
    Boolean enterStore(Integer storeId);
    Set<Sale> excursionPublicOrder(String deliveryId);
    Boolean acceptOrder(String saleName, String deliveryId);
    Boolean terminateOrder(String saleName);
}
