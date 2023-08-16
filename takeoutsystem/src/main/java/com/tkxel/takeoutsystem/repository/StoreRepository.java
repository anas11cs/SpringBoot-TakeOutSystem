package com.tkxel.takeoutsystem.repository;

import com.tkxel.takeoutsystem.entity.Store;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface StoreRepository extends JpaRepository<Store,Integer> {
    @Query("SELECT r FROM Store r left join fetch r.storeToSale " +
            "left join fetch r.storeToItem " +
            "left join fetch r.storeToProductCatalog WHERE r.id = :id")
    Store findStoreById(@Param("id") Integer id);
}