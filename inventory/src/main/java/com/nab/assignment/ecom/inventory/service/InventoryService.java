package com.nab.assignment.ecom.inventory.service;

import com.nab.assignment.ecom.inventory.model.Inventory;
import com.nab.assignment.ecom.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {
    private final InventoryRepository repository;

    public boolean isInStock(Long productID, int quantity) {
        return repository.findById(productID).stream().anyMatch(a -> a.getQuantity() >= quantity);
    }

    public Inventory addToStock(Long productID, int providedQuantity) {
        Optional<Inventory> inventoryProduct = repository.findById(productID);
        if (inventoryProduct.isPresent()) {
            Inventory inventory = inventoryProduct.get();
            int currentQuantity = inventory.getQuantity();
            int newQuantity = currentQuantity + providedQuantity;
            inventory.setQuantity(newQuantity);
            return repository.save(inventory);
        } else {
            throw new IllegalArgumentException("No matching product found for id: " + productID);
        }
    }

    public boolean subtractFromStock(Long productID, int requestedQuantity, AtomicReference<BigDecimal> finalPrice) {
        Optional<Inventory> inventoryProduct = repository.findByProductId(productID);
        if (inventoryProduct.isPresent()) {
            Inventory inventory = inventoryProduct.get();
            int currentQuantity = inventory.getQuantity();
            int newQuantity = currentQuantity - requestedQuantity;
            if (newQuantity > 0) {
                inventory.setQuantity(newQuantity);
                repository.save(inventory);
                finalPrice.getAndUpdate(current -> inventory.getProduct().getPrice().multiply(BigDecimal.valueOf(requestedQuantity)));
                return true;
            }
        }
        return false;
    }
}
