package com.cookswp.milkstore.pojo.dtos.PaymentModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class PaymentDTO {
    public String code;
    public String message;
    public int userID;
    public String paymentUrl;
}