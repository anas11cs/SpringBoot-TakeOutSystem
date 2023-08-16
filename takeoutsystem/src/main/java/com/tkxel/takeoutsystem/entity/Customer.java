package com.tkxel.takeoutsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    private String id;
    private String customerAddress;
    private String customerName;

    public Customer(Customer customer){
        this.id = customer.getId();
        this.customerAddress = customer.getCustomerAddress();
        this.customerName = customer.getCustomerName();
    }
}
