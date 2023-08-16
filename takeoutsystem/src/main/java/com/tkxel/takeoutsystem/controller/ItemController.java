package com.tkxel.takeoutsystem.controller;

import com.tkxel.takeoutsystem.dto.ItemDto;
import com.tkxel.takeoutsystem.entity.Item;
import com.tkxel.takeoutsystem.service.ManageItemService;
import com.tkxel.takeoutsystem.service.ProcessOrderService;
import com.tkxel.takeoutsystem.service.TakeOutSystem;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@Log4j2
public class ItemController {

    @Autowired
    ManageItemService manageItemService;
    @Autowired
    ProcessOrderService processOrderService;
    @Autowired
    TakeOutSystem takeOutSystem;

    @GetMapping("/takeoutsystem/searchitem")
    public ResponseEntity<Item> findItem(@RequestParam("itemname") String itemName){
        try{
            Item searchedItem = takeOutSystem.search(itemName);
            return searchedItem.getId() == null  ?
                    new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>(searchedItem, HttpStatus.OK);
        }
        catch(Exception exception){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/processorderservice/enteritem")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> enterItem(@RequestParam("saleId") Integer saleId,@RequestParam("itemId") Integer itemId, @RequestParam("quantity") Integer itemQuantity){
        try{
            Boolean itemEnterSuccess = processOrderService.enterItem(saleId, itemId, itemQuantity);
            return (itemEnterSuccess) ?
                    new ResponseEntity<>(true, HttpStatus.OK) :
                    new ResponseEntity<>(HttpStatus.BAD_REQUEST)
                    ;
        }
        catch(Exception exception){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/manageitemcrudservice/createitem")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addItem(@RequestBody Item itemObject, @RequestParam("storeId") Integer storeId) {
        try {
            log.info("Item Details Received - ItemName:" + itemObject.getItemName());
            Item itemAddedSuccess = manageItemService.createItem(itemObject , storeId);
            return (itemAddedSuccess.getId() == null) ?
                    new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>(itemAddedSuccess.getItemName() + " Added Successfully as Item" , HttpStatus.OK);
        } catch (Exception exception) {
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/manageitemcrudservice/queryitem")
    public ResponseEntity<ItemDto> getItemById(@RequestParam("itemId") Integer itemId){
        try{
            ItemDto queriedItem = manageItemService.queryItem(itemId);
            return (queriedItem.getId() == null) ?
                    new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>(queriedItem, HttpStatus.OK);
        }
        catch(Exception exception){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/manageitemcrudservice/modifyitem")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateItem(@RequestBody Item item, @RequestParam Integer storeId){
        try{
            log.info("Item Details Received for - ItemId:"+item.getId());
            Item updatedItem = manageItemService.modifyItem(item , storeId);
            return (updatedItem.getId() == null) ?
                    new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>(item.getItemName() + " Updated/Added Successfully", HttpStatus.OK);
        }
        catch(Exception exception){
            return new ResponseEntity<>("Store Does Not Exist!",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/manageitemcrudservice/deleteitem")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> removeItem(@RequestParam Integer itemId){
        try{
            Boolean itemDeletedSuccess = manageItemService.deleteItem(itemId);
            return (!itemDeletedSuccess) ?
                    new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>("Item with id:" +itemId + " deleted Successfully!", HttpStatus.OK);
        }
        catch(Exception exception){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
