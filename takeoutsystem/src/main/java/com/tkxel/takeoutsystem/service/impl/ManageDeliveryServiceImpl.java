package com.tkxel.takeoutsystem.service.impl;

import com.tkxel.takeoutsystem.entity.Delivery;
import com.tkxel.takeoutsystem.repository.DeliveryRepository;
import com.tkxel.takeoutsystem.service.ManageDeliveryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ManageDeliveryServiceImpl implements ManageDeliveryService {
    @Autowired
    DeliveryRepository deliveryRepository;
    @Override
    public Boolean createDelivery(Delivery deliveryObject) {
        Boolean isDeliveryIdPresent = deliveryRepository.existsById(deliveryObject.getId());
        if(deliveryObject.getId()!=null && !isDeliveryIdPresent && deliveryObject.getDeliveryName()!=null && deliveryObject.getAttribute()!=null) {
            deliveryRepository.save(deliveryObject);
            log.info("Delivery Created for:"+deliveryObject.getId());
            return true;
        }
        log.warn("Delivery Object Already Exists:" + isDeliveryIdPresent + " or Delivery Object Not Fit for Database Entry Delivery Id Received:"+deliveryObject.getId());
        return false;
    }
}
