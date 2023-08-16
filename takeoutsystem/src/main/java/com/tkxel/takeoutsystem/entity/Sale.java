package com.tkxel.takeoutsystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate saleTime;
    private Boolean isComplete;
    private BigDecimal saleAmount;
    private Boolean isReadyToPay;
    private Boolean isAccept;
    @Column(unique = true)
    private String saleName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "orderLineItemtoSale")
    private Set<OrderLineItem> saleToOrderLineItem;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    @JsonIgnore
    private Payment saleToPayment;

    @ManyToOne
    @JoinColumn(name = "store_id")
    @JsonIgnore
    private Store saleToStore;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="customer_id")
    private Customer saleToCustomer;

    @ManyToOne
    @JoinColumn(name = "delivery_id")
    @JsonIgnore
    private Delivery saleToDelivery;

}
