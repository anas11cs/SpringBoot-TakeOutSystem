package com.tkxel.takeoutsystem.repository;

import com.tkxel.takeoutsystem.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Query("SELECT r FROM Item r left join fetch r.itemToProductCatalog " +
            "left join fetch r.itemToStore  WHERE r.id = :id")
    Item findItemById(@Param("id") Integer id);

    Item findByItemName(String itemName);
}