package com.cookswp.milkstore.pojo.dtos.OrderModel;

import com.cookswp.milkstore.enums.FailureReason;
import com.cookswp.milkstore.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    //    private Long id;
    private int userId;
    //    private int customerPaymentMethodId;
    private String shippingAddress;
    private Status status;
    private Integer voucherId;
    private Integer cartId;
    //    private BigDecimal shippingFee; // tam thoi ko co ship fee
    private BigDecimal totalPrice;
    private String shippingCode;
    private String receiverName;
    private String receiverPhone;
    private LocalDateTime orderDate;
    private FailureReason failureReason;
    private String failureReasonNote;
}
