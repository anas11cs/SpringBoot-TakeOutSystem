package com.tkxel.takeoutsystem.service.impl;

import com.tkxel.takeoutsystem.dto.ItemDto;
import com.tkxel.takeoutsystem.entity.Item;
import com.tkxel.takeoutsystem.entity.Store;
import com.tkxel.takeoutsystem.repository.ItemRepository;
import com.tkxel.takeoutsystem.repository.StoreRepository;
import com.tkxel.takeoutsystem.service.ManageItemService;
import com.tkxel.takeoutsystem.utils.TakeOutSystemUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class ManageItemServiceImpl implements ManageItemService {
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    TakeOutSystemUtils takeOutSystemUtils;

    @Autowired
    StoreRepository storeRepository;

    @Override
    public Item createItem(Item item , Integer storeId) {
        try {
            Store store = storeRepository.findStoreById(storeId);
            if (store != null) {
                log.info("Store found for storeId:"+storeId);
                item.setItemToStore(store);
                return itemRepository.save(item);
            }
            log.info("Store found is null for storeId:"+storeId);
        }
        catch (Exception exception){
            log.error(exception);
        }
        return new Item();
    }

    @Override
    public ItemDto queryItem(Integer itemId) {
        log.info("Querying Item with ItemId:"+itemId);
        Item item = itemRepository.findItemById(itemId);
        ItemDto queriedItem = new ItemDto();
        if(item!=null){
            log.info("Mapping Item Details for Item Id:"+item.getId());
            queriedItem.itemToItemDtoMapper(item);
        }
        return queriedItem;
    }

    @Override
    public Item modifyItem(Item item , Integer storeId) {
        Store store = storeRepository.findStoreById(storeId);
        if(store!=null && store.getId()!=null) {
            item.setItemToStore(store);
            return itemRepository.save(item);
        }
        log.info("Store not found for StoreId:"+store.getId());
        return new Item();
    }

    @Override
    public Boolean deleteItem(Integer itemId) {
        try {
            Optional<Item> itemFetched= itemRepository.findById(itemId);
            if (takeOutSystemUtils.isNotEmptyOrNullOptional(itemFetched)) {
                log.info("Item Found by Id:" + itemId + " Deleting the Item in Next Step!");
                itemRepository.deleteById(itemId);
                return true;
            }
            log.warn("Item Not Found by ItemId:" + itemId);
        }
        catch (Exception exception) {
            log.error(exception);
        }
        return false;
    }
}
