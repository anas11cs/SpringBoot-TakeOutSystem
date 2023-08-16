package com.tkxel.takeoutsystem.repository;

import com.tkxel.takeoutsystem.entity.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer>{
    @Query("SELECT r FROM Sale r left join fetch r.saleToOrderLineItem " +
            "left join fetch r.saleToPayment " +
            "left join fetch r.saleToStore " +
            "left join fetch r.saleToCustomer "+
            "left join fetch r.saleToDelivery WHERE r.id = :id")
    Sale findSaleById(@Param("id") Integer id);

    @Query("SELECT r FROM Sale r left join fetch r.saleToOrderLineItem " +
            "left join fetch r.saleToPayment " +
            "left join fetch r.saleToStore " +
            "left join fetch r.saleToCustomer "+
            "left join fetch r.saleToDelivery WHERE r.saleName = :saleName")
    Sale findSaleBySaleName(@Param("saleName") String saleName);

    @Query("SELECT r FROM Sale r left join fetch r.saleToOrderLineItem " +
            "left join fetch r.saleToPayment " +
            "left join fetch r.saleToStore " +
            "left join fetch r.saleToCustomer "+
            "left join fetch r.saleToDelivery d WHERE d.id = :delivery_id")
    Set<Sale> findSaleByDeliveryId(@Param("delivery_id") String delivery_id);
}
