package com.nab.assignment.ecom.inventory.controller;

import com.nab.assignment.ecom.inventory.model.Inventory;
import com.nab.assignment.ecom.inventory.service.InventoryService;
import com.nab.assignment.ecom.inventory.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService service;

    public boolean isInStock(Long productID, int quantity) {
        return service.isInStock(productID, quantity);
    }

    public Inventory addToStock(Long productID, int providedQuantity) {
        return service.addToStock(productID, providedQuantity);
    }

    public boolean subtractFromStock(Long productID, int requestedQuantity, AtomicReference<BigDecimal> finalPrice) {
        return service.subtractFromStock(productID, requestedQuantity, finalPrice);
    }
}
