package com.tkxel.takeoutsystem.dto;

import com.tkxel.takeoutsystem.entity.Delivery;
import com.tkxel.takeoutsystem.entity.Sale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
public class DeliveryDto {
    private String id;
    private String deliveryName;
    private String attribute;
    private Set<Sale> deliveryToSale;

    public void deliveryToDeliveryDtoMapper(Delivery delivery){
        log.info("Mapping Delivery for DeliveryId:" + delivery.getId());
        delivery.setId(this.getId());
        delivery.setDeliveryName(this.getDeliveryName());
        delivery.setAttribute(this.getAttribute());
    }

    public Delivery deliveryDtoToDeliveryMapper(){
        log.info("Mapping DeliveryDto for DeliveryId:" + this.getId());
        Delivery delivery = new Delivery();
        delivery.setId(this.getId());
        delivery.setDeliveryName(this.getDeliveryName());
        delivery.setAttribute(this.getAttribute());
        delivery.setDeliveryToSale(this.getDeliveryToSale());
        return delivery;
    }

}
