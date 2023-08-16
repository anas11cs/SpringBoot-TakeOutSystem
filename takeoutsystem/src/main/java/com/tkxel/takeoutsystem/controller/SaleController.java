package com.tkxel.takeoutsystem.controller;

import com.tkxel.takeoutsystem.entity.Sale;
import com.tkxel.takeoutsystem.service.ProcessOrderService;
import com.tkxel.takeoutsystem.service.TakeOutSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Set;

@RestController
public class SaleController {

    @Autowired
    ProcessOrderService processOrderService;
    @Autowired
    TakeOutSystem takeOutSystem;

    @GetMapping("/takeoutsystem/excursionpublicorder")
    public ResponseEntity<Set<Sale>> getAllSalesByDelivery(@RequestParam("deliveryId") String deliveryId){
        try{
            Set<Sale> allSalesByDelivery = takeOutSystem.excursionPublicOrder(deliveryId);
            return (allSalesByDelivery == null || allSalesByDelivery.isEmpty())  ?
                    new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                    new ResponseEntity<>(allSalesByDelivery, HttpStatus.OK);
        }
        catch(Exception exception){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("processorderservice/makeneworder")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addNewOrder(@RequestParam("storeId") Integer storeId, @RequestBody Sale sale){
        try{
            Boolean OrderCreated = processOrderService.makeNewOrder(storeId, sale);
            return (OrderCreated == null || !OrderCreated.booleanValue())  ?
                    new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>("Order Added Successfully! for StoreId:"+storeId, HttpStatus.OK);
        }
        catch(Exception exception){
            return new ResponseEntity<>(exception.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("processorderservice/endorder")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BigDecimal> updateOrderEnd(@RequestParam("saleId") Integer saleId){
        try{
            BigDecimal totalSaleAmount = processOrderService.endOrder(saleId);
            return (totalSaleAmount.compareTo(BigDecimal.ZERO) == 0) ?
                    new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>(totalSaleAmount, HttpStatus.OK);
        }
        catch(Exception exception){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
