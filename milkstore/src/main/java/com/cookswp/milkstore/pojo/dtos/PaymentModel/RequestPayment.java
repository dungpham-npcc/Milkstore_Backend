package com.cookswp.milkstore.pojo.dtos.PaymentModel;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class RequestPayment {
    //userID
    private int userID;

    private long amount;

    private String bankCode;

}
