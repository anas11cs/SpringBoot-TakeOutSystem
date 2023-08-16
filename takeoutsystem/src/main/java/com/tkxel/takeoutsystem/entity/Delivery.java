package com.tkxel.takeoutsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Delivery {
    @Id
    private String id;
    @Column(unique = true)
    private String deliveryName;
    private String attribute;

    @OneToMany(cascade= {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY, mappedBy = "saleToDelivery")
    private Set<Sale> deliveryToSale;

    public Delivery(Delivery delivery){
        this.id = delivery.getId();
        this.deliveryName = delivery.getDeliveryName();
        this.attribute = delivery.getAttribute();
    }
}
