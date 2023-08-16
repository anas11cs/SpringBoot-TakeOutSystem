package com.tkxel.takeoutsystem.controller;

import com.tkxel.takeoutsystem.entity.Delivery;
import com.tkxel.takeoutsystem.service.ManageDeliveryService;
import com.tkxel.takeoutsystem.service.TakeOutSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeliveryController {

    @Autowired
    TakeOutSystem takeOutSystem;
    @Autowired
    ManageDeliveryService manageDeliveryService;

    @PostMapping("/takeoutsystem/acceptorder")
    public ResponseEntity<Boolean> assignDeliveryToOrder(@RequestParam("saleName") String saleName, @RequestParam("deliveryId") String deliveryId){
        try{
            Boolean saleFoundAndAccepted = takeOutSystem.acceptOrder(saleName, deliveryId);
            return (saleFoundAndAccepted == null || !saleFoundAndAccepted.booleanValue())  ?
                    new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                    new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch(Exception exception){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/takeoutsystem/terminateorder")
    public ResponseEntity<Boolean> markOrderComplete(@RequestParam("saleName") String saleName){
        try{
            Boolean saleFoundAndCompleted = takeOutSystem.terminateOrder(saleName);
            return (saleFoundAndCompleted == null || !saleFoundAndCompleted.booleanValue())  ?
                    new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                    new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch(Exception exception){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/managediliverycrudservice/createdilivery")
    public ResponseEntity<Boolean> addDelivery(@RequestBody Delivery deliveryObject){
        try{
            Boolean deliveryAddedSuccess = manageDeliveryService.createDelivery(deliveryObject);
            return (deliveryAddedSuccess == null || !deliveryAddedSuccess) ?
                    new ResponseEntity<>(HttpStatus.BAD_REQUEST) :
                    new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch(Exception exception){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
