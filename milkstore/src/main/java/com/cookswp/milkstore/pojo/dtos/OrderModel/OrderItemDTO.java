package com.cookswp.milkstore.pojo.dtos.OrderModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class OrderItemDTO {
    private int productId;
    private String productName;
    private int quantity;
    private BigDecimal price;
}
