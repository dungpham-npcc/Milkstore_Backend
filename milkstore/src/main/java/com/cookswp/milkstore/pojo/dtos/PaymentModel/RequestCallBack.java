package com.cookswp.milkstore.pojo.dtos.PaymentModel;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestCallBack {

    private long amount;
    private String bankCode;
    private String bankTranNo;
    private String cardType;
    private String orderInfo;
    private String payDate;
    private String transactionNo;
    private String transactionStatus;
    private String txnRef;
    private String responseCode;

}
