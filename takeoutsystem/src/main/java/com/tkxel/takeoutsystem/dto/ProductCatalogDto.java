package com.tkxel.takeoutsystem.dto;

import com.tkxel.takeoutsystem.entity.Item;
import com.tkxel.takeoutsystem.entity.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
public class ProductCatalogDto {
    private Integer id;
    private String productCatalogName;
    private Set<Item> productCatalogToItem;
    private Store productCatalogToStore;
}
