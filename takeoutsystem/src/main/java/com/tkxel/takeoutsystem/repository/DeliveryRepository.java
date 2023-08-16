package com.tkxel.takeoutsystem.repository;

import com.tkxel.takeoutsystem.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, String> {
    @Query("SELECT r FROM Delivery r left join fetch r.deliveryToSale WHERE r.id = :id ")
    Delivery findDeliveryById(@Param("id") String id);
}
