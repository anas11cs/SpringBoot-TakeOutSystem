package com.tkxel.takeoutsystem.service.impl;

import com.tkxel.takeoutsystem.entity.Delivery;
import com.tkxel.takeoutsystem.entity.Item;
import com.tkxel.takeoutsystem.entity.Sale;
import com.tkxel.takeoutsystem.repository.DeliveryRepository;
import com.tkxel.takeoutsystem.repository.ItemRepository;
import com.tkxel.takeoutsystem.repository.SaleRepository;
import com.tkxel.takeoutsystem.repository.StoreRepository;
import com.tkxel.takeoutsystem.service.TakeOutSystem;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Log4j2
public class TakeOutSystemImpl implements TakeOutSystem {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    SaleRepository saleRepository;
    @Autowired
    DeliveryRepository deliveryRepository;
    @Override
    public Item search(String itemName) {
        Item itemFoundByName = itemRepository.findByItemName(itemName);
        if(itemFoundByName == null){
            return new Item();
        }
        return itemFoundByName;
    }

    @Override
    public Boolean enterStore(Integer storeId) {
        try{
            return storeRepository.existsById(storeId);
        }
        catch(Exception exception){
            log.error(exception);
        }
        return false;
    }

    @Override
    @Transactional
    public Set<Sale> excursionPublicOrder(String deliveryId) {
        Set<Sale> allSales = saleRepository.findSaleByDeliveryId(deliveryId);
        if(allSales==null){
            return allSales = new HashSet<Sale>();
        }
        return allSales;
    }

    @Override
    @Transactional
    public Boolean acceptOrder(String saleName , String deliveryId) {
        try{
            Sale sale = saleRepository.findSaleBySaleName(saleName);
            Delivery delivery = deliveryRepository.findDeliveryById(deliveryId);
            if(sale != null && sale.getIsAccept()!=null && !sale.getIsAccept() && sale.getSaleToOrderLineItem()!=null && sale.getSaleToOrderLineItem().size() > 0 && delivery!=null){
                sale.setSaleToDelivery(delivery);
                sale.setIsAccept(true);
                if(delivery.getDeliveryToSale()==null){
                    delivery.setDeliveryToSale(new HashSet<Sale>());
                }
                delivery.getDeliveryToSale().add(sale);
                deliveryRepository.save(delivery);
                saleRepository.save(sale);
                return true;
            }
            log.warn("Sale with the given Name:" + saleName + " doesn't Exist or doesn't have Order with Status:False or There Exists no Order with the Sale or Delivery doesn't exist with the Id:"+deliveryId);
        }
        catch (Exception exception){
            log.error(exception);
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean terminateOrder(String saleName) {
        try{
            Sale sale = saleRepository.findSaleBySaleName(saleName);
            if(sale != null && sale.getIsAccept()!=null && sale.getIsAccept() && sale.getSaleToOrderLineItem()!=null && sale.getSaleToOrderLineItem().size() > 0 && sale.getSaleToDelivery()!=null && (sale.getIsComplete()==null || !sale.getIsComplete())){
                sale.setIsComplete(true);
                saleRepository.save(sale);
                return true;
            }
            log.warn("Sale with the given Name:" + saleName + " doesn't Exist or doesn't have Order with Status:True or There Exists no Order with the Sale or Delivery doesn't exist with the Sale or Sale is already IsCompleted=True");
        }
        catch (Exception exception){
            log.error(exception);
        }
        return false;
    }
}
