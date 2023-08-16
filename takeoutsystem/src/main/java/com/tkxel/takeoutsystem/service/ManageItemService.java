package com.tkxel.takeoutsystem.service;

import com.tkxel.takeoutsystem.dto.ItemDto;
import com.tkxel.takeoutsystem.entity.Item;

public interface ManageItemService {
    Item createItem(Item item , Integer storeId);
    ItemDto queryItem(Integer itemId);
    Item modifyItem(Item item , Integer storeId);
    Boolean deleteItem(Integer itemId);
}
