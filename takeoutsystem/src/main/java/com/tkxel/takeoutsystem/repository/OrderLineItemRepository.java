package com.tkxel.takeoutsystem.repository;

import com.tkxel.takeoutsystem.entity.OrderLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineItemRepository extends JpaRepository<OrderLineItem, Integer> {
}
