package com.tkxel.takeoutsystem.repository;

import com.tkxel.takeoutsystem.entity.ProductCatalog;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface ProductCatalogRepository extends JpaRepository<ProductCatalog, Integer>{
}
