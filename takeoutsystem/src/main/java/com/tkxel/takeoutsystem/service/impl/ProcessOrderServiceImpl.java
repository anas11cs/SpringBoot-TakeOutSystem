package com.tkxel.takeoutsystem.service.impl;

import com.tkxel.takeoutsystem.dto.ItemDto;
import com.tkxel.takeoutsystem.dto.SaleDto;
import com.tkxel.takeoutsystem.entity.*;
import com.tkxel.takeoutsystem.repository.ItemRepository;
import com.tkxel.takeoutsystem.repository.OrderLineItemRepository;
import com.tkxel.takeoutsystem.repository.SaleRepository;
import com.tkxel.takeoutsystem.repository.StoreRepository;
import com.tkxel.takeoutsystem.service.ProcessOrderService;
import com.tkxel.takeoutsystem.utils.TakeOutSystemUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;

@Service
@Log4j2
public class ProcessOrderServiceImpl implements ProcessOrderService {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderLineItemRepository orderLineItemRepository;

    @Autowired
    TakeOutSystemUtils utils;

    @Override
    public Boolean makeNewOrder(Integer storeId , Sale saleObject) {
        try {
            log.info("Adding New Order for storeId:" + storeId + " and sale of Name:" + saleObject.getSaleName());
            Optional<Store> orderStore = storeRepository.findById(storeId);
            log.info("Store Fetched from Db:" + orderStore);
            if (utils.isNotEmptyOrNullOptional(orderStore)) {
                saleObject.setSaleTime(LocalDate.now());
                saleObject.setSaleToStore(orderStore.get());
                saleObject.setIsComplete(false);
                saleObject.setIsReadyToPay(false);
                saleRepository.save(saleObject);
                log.info("Sale Added for Store Id:"+storeId);
                return true;
            }
            log.warn("Store not found with storeId:"+storeId);
        }
        catch (Exception exception){
            log.error(exception);
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean enterItem(Integer saleId, Integer itemId , Integer itemQuantity) {
        // Enter Item to Order
        try {
            Sale saleObject = saleRepository.findSaleById(saleId);
            SaleDto saleDto = new SaleDto();
            Item itemObject = itemRepository.findItemById(itemId);
            ItemDto itemDto = new ItemDto();

            if (saleObject !=null && itemObject!=null && itemObject.getStockNumber() > 0 && (itemObject.getStockNumber() - itemQuantity >=0)) {
                saleDto.saleToSaleDtoMapper(saleObject);
                itemDto.itemToItemDtoMapper(itemObject);

                itemObject.setStockNumber(itemObject.getStockNumber() - itemQuantity);

                if (saleObject.getSaleToOrderLineItem() == null || saleObject.getSaleToOrderLineItem().isEmpty()) {
                    saleObject.setSaleToOrderLineItem(new HashSet<OrderLineItem>());
                }

                OrderLineItem orderLineItem = new OrderLineItem();
                orderLineItem.setQuantity(itemQuantity);
                orderLineItem.setSubAmount(BigDecimal.valueOf(itemQuantity).multiply(itemDto.getItemPrice()));
                orderLineItem.setOrderLineItemtoItem(itemObject);

                saleObject.getSaleToOrderLineItem().add(orderLineItem);
                orderLineItem.setOrderLineItemtoSale(saleObject);

                itemRepository.save(itemObject);
                orderLineItemRepository.save(orderLineItem);
                saleRepository.save(saleObject);
                return true;
            }
            log.warn("Attempting to Enter Item Failed - Due to any of the Reason: sale not found OR item not found OR Stock In-Sufficient");
        }
        catch (Exception exception){
            log.error(exception);
        }
        return false;
    }

    @Override
    public BigDecimal endOrder(Integer saleId) {
        Sale sale = saleRepository.findSaleById(saleId);
        if(sale!=null){
            sale.setIsReadyToPay(true);
            sale.setSaleAmount(sale.getSaleToOrderLineItem()
                                          .stream().map(OrderLineItem::getSubAmount)
                                          .reduce(BigDecimal.ZERO,BigDecimal::add));
            saleRepository.save(sale);
            return sale.getSaleAmount();
        }
        return BigDecimal.ZERO;
    }

    @Override
    @Transactional
    public Boolean makeCashPayment(BigDecimal payableAmount , Integer saleId) {
        try{
            Sale sale = saleRepository.findSaleById(saleId);
            if((sale!=null) && (sale.getIsReadyToPay()!=null && sale.getIsReadyToPay()) && (sale.getIsComplete()!=null && !sale.getIsComplete()) && (payableAmount.compareTo(sale.getSaleAmount()) == 1 || payableAmount.compareTo(sale.getSaleAmount()) == 0)){
                // Changes
                CashPayment cashPayment = new CashPayment(payableAmount, payableAmount.subtract(sale.getSaleAmount()));
                sale.setSaleToPayment(cashPayment);
                sale.setIsAccept(false);
                // Changes
                cashPayment.setPaymentToSale(sale);
                saleRepository.save(sale);
                log.info("Cash Payment Balance is:" + cashPayment.getBalance());
                return true;
            }
            log.warn("makeCashPayment : Sale Object Doesn't Exist or is not Valid for Payment with SaleId:"+saleId+ " & IsOrderComplete Status:" + sale.getIsComplete() + " & Payment Status: " + sale.getIsReadyToPay());
        }
        catch(Exception exception){
            log.error(exception);
        }
        return false;
    }

    @Override
    public Boolean makeCardPayment(BigDecimal payableAmount, String cardAccountNumber , LocalDate expireDate , Integer saleId) {
        try{
            Sale sale = saleRepository.findSaleById(saleId);
            if((sale!=null) && (sale.getIsReadyToPay()!=null && sale.getIsReadyToPay()) && (sale.getIsComplete()!=null && sale.getIsComplete())){
                // Implementation
                saleRepository.save(sale);
            }
            log.warn("makeCardPayment : Sale Object Doesn't Exist or is not Valid for Payment with SaleId:"+saleId+ " & IsOrderComplete Status:" + sale.getIsComplete() + " & Payment Status: " + sale.getIsReadyToPay());
        }catch(Exception exception){
            log.error(exception);
        }
        return false;
    }
}
