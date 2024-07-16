package com.cookswp.milkstore.pojo.dtos.OrderModel;

import com.cookswp.milkstore.pojo.entities.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.annotation.Order;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {

    private String receiverName;
    private String receiverPhoneNumber;
    private BigDecimal totalPrice;
    private String shippingAddress;
    private List<OrderModel> cartID;
    private List<OrderItemDTO> items;

    @Getter
    @Setter
    public static class OrderModel {
        private int productID;
        private String productName;
        private int quantity;
        private BigDecimal unitPrice;
    }

    // Getters and setters
}
