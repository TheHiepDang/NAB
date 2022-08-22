package com.nab.assignment.ecom.order.dto;

import lombok.Data;

@Data
public class AddProductDTO {
    private Long userId;
    private Long productID;
    private int quantity;
}
