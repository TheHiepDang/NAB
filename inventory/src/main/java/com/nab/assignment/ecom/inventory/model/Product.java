package com.nab.assignment.ecom.inventory.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Data
public class Product {
    @Id
    private Long id;

    private String category;

    private String name;
    private BigDecimal price;
    private String brand;
    private String colour;
    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    @OneToOne(mappedBy = "product")
    private Inventory inventory;
}
