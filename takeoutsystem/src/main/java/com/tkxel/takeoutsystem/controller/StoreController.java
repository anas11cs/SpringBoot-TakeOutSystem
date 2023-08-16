package com.tkxel.takeoutsystem.controller;

import com.tkxel.takeoutsystem.dto.StoreDto;
import com.tkxel.takeoutsystem.entity.Store;
import com.tkxel.takeoutsystem.service.ManageStoreService;
import com.tkxel.takeoutsystem.service.TakeOutSystem;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
public class StoreController {

    @Autowired
    ManageStoreService manageStoreService;
    @Autowired
    TakeOutSystem takeOutSystem;

    @GetMapping("/takeoutsystem/enterstore")
    public ResponseEntity<Boolean> verifyStore(@RequestParam("storeId") Integer storeId){
        try{
            Boolean storeFound = takeOutSystem.enterStore(storeId);
            return (storeFound == null || !storeFound.booleanValue())  ?
                    new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch(Exception exception){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/managestorecrudservice/createstore")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addStore(@RequestBody Store storeObject){
        try{
            log.info("Store Details Recieved - StoreName:"+storeObject.getStoreName()+" & Store Address:"+storeObject.getStoreAddress()+" & Store Opened:"+storeObject.getIsOpened());
            Store storeAddedSuccess = manageStoreService.createStore(storeObject);
            return (storeAddedSuccess == null) ?
                    new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>(storeAddedSuccess.getStoreName() + " Store Added Successfully, for Location: "+storeAddedSuccess.getStoreAddress(), HttpStatus.OK);
        }
        catch(Exception exception){
            return new ResponseEntity<>("Store Already Exists or System Malfunction!",HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/managestorecrudservice/querystore")
    public ResponseEntity<StoreDto> getStoreById(@RequestParam("storeId") Integer storeId){
        try{
            log.info("Hunting Store Details for StoreId:"+storeId);
            StoreDto queriedStore = manageStoreService.queryStore(storeId);
            return (queriedStore == null || queriedStore.getId() == null) ?
                    new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>(queriedStore, HttpStatus.OK);
        }
        catch(Exception exception){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/managestorecrudservice/modifystore")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateStore(@RequestBody Store updatedStoreObject){
        try{
            log.info("Store Details Recieved - StoreName:"+updatedStoreObject.getStoreName()+" & Store Address:"+updatedStoreObject.getStoreAddress()+" & Store Opened:"+updatedStoreObject.getIsOpened());
            Store updatedStore = manageStoreService.modifyStore(updatedStoreObject);
            return (updatedStore == null) ?
                    new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>(updatedStore.getStoreName() + " Store Updated/Added(if id not present) Successfully, for Location: "+updatedStore.getStoreAddress(), HttpStatus.OK);
        }
        catch(Exception exception){
            return new ResponseEntity<>("Store Already Exists With Same Properties!",HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/managestorecrudservice/deletestore")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> removeStore(@RequestParam("storeId") Integer storeId){
        try{
            log.info("Store Details Recieved for Deletion - storeId:"+storeId);
            Boolean storeDeletedSuccess = manageStoreService.deleteStore(storeId);
            return (storeDeletedSuccess == null || !storeDeletedSuccess) ?
                    new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>("Store Deleted Successfully - by Store Id:" + storeId, HttpStatus.OK);
        }
        catch(Exception exception){
            return new ResponseEntity<>("Store Not Found for Deletion",HttpStatus.NOT_FOUND);
        }
    }

}
