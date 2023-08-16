package com.tkxel.takeoutsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String itemName;
    private BigDecimal itemPrice;
    private Integer stockNumber;
    private BigDecimal orderPrice;

    @ManyToOne
    @JoinColumn(name = "product_cat_id")
    @JsonIgnore
    private ProductCatalog itemToProductCatalog;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonIgnore
    private Store itemToStore;

}
