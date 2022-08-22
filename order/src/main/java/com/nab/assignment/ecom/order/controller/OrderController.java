package com.nab.assignment.ecom.order.controller;

import com.nab.assignment.ecom.order.dto.AddProductDTO;
import com.nab.assignment.ecom.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping("/addProductToOrder")
    public void addProductToOrder(@RequestBody AddProductDTO addProductDTO) {
        service.addProductToOrder(addProductDTO.getUserId(), addProductDTO.getProductID(), addProductDTO.getQuantity());
    }

    @PostMapping("/checkOut")
    public void checkout(Long orderId) {
        service.checkout(orderId);
    }
}
