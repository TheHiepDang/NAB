package com.nab.assignment.ecom.inventory.service;

import com.nab.assignment.ecom.inventory.dto.Product;
import com.nab.assignment.ecom.inventory.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository repository;
    private final ModelMapper modelMapper;

    public List<Product> getAllProducts(Pageable pageable) {
        return repository.findAll(pageable).get().map(entity -> modelMapper.map(entity, Product.class)).collect(Collectors.toList());
    }

    public Product getProductDetails(Long id) {
        AtomicReference<Product> product = new AtomicReference<>(new Product());
        repository.findById(id).ifPresent(matchedProduct -> product.set(modelMapper.map(matchedProduct, Product.class)));
        return product.getPlain();
    }

    public Long addProduct(Product newProduct) {
        com.nab.assignment.ecom.inventory.model.Product persistedProduct = repository.save(modelMapper.map(newProduct, com.nab.assignment.ecom.inventory.model.Product.class));
        return persistedProduct.getId();
    }

}
