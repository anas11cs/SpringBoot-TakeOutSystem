package com.tkxel.takeoutsystem.controller;

import com.tkxel.takeoutsystem.service.ProcessOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class PaymentController {

    @Autowired
    ProcessOrderService processOrderService;
    @PostMapping("/processorderservice/makecashpayment")
    public ResponseEntity<Boolean> cashPayment(@RequestParam("amount") BigDecimal amount, @RequestParam("saleId") Integer saleId){
        try{
            Boolean orderVerifiedAndCashPaid = processOrderService.makeCashPayment(amount, saleId);
            return (orderVerifiedAndCashPaid == null || !orderVerifiedAndCashPaid) ?
                    new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                    new ResponseEntity<>(true, HttpStatus.OK);
        }
        catch(Exception exception){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/processorderservice/makecardpayment")
    public ResponseEntity<Boolean> cardPayment(@RequestParam("amount") BigDecimal amount, @RequestParam("cardAccountNumber") String cardAccountNumber, @RequestParam("expireDate") String expireDate , @RequestParam("saleId") String saleId){
        try{
            return new ResponseEntity<>(false, HttpStatus.SERVICE_UNAVAILABLE);
        }
        catch(Exception exception){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
