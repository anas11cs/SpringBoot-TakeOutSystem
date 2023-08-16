package com.tkxel.takeoutsystem.service.impl;

import com.tkxel.takeoutsystem.dto.StoreDto;
import com.tkxel.takeoutsystem.entity.Store;
import com.tkxel.takeoutsystem.repository.StoreRepository;
import com.tkxel.takeoutsystem.service.ManageStoreService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2
public class ManageStoreServiceImpl implements ManageStoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Override
    public Store createStore(Store store) {
        return storeRepository.save(store);
    }

    @Override
    @Transactional
    public StoreDto queryStore(Integer storeId) {
        try {
            Store store = storeRepository.findStoreById(storeId);
            StoreDto storeUpdated = new StoreDto();
            if (store != null) {
                log.info("Store Found & Mapping Store Details for Store Id:" + store.getId());
                storeUpdated.storeToStoreDtoMapper(store);
                return storeUpdated;
            }
            log.warn("Store not Found for Store Id:"+storeId);
        }
        catch (Exception exception){
            log.error(exception);
        }
        return new StoreDto();
    }

    @Override
    public Store modifyStore(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public Boolean deleteStore(Integer storeId) {
        if(storeRepository.findById(storeId).get() != null) {
            log.info("Store Found by Id:" + storeId + " Deleting the Store Next!");
            storeRepository.deleteById(storeId);
            return true;
        }
        log.warn("Store Not Found by Id:"+ storeId);
        return false;
    }
}
