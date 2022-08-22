package com.nab.assignment.ecom.webapp.repository;

import com.nab.assignment.ecom.webapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByUsername(String username);
}
