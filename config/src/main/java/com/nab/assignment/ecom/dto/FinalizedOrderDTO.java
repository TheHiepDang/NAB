package com.nab.assignment.ecom.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinalizedOrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private OrderDTO order;
    private OrderDTO finalizedOrder;
    private BigDecimal finalPrice;
}
