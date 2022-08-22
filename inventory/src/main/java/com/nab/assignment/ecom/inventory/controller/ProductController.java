package com.nab.assignment.ecom.inventory.controller;

import com.nab.assignment.ecom.inventory.dto.Product;
import com.nab.assignment.ecom.inventory.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping("/getAllProducts")
    public List<Product> getAllProducts(Pageable pageable) {
        return service.getAllProducts(pageable);
    }

    public Product getProductDetails(Long id) {
        return service.getProductDetails(id);
    }

    public Long addProduct(Product newProduct) {
        return addProduct(newProduct);
    }
}
