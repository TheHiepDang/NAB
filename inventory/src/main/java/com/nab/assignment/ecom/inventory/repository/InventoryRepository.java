package com.nab.assignment.ecom.inventory.repository;

import com.nab.assignment.ecom.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

//    @Query("from Inventory i inner join fetch i.product where i.product.id = :Id")
    Optional<Inventory> findByProductId(Long Id);
}
