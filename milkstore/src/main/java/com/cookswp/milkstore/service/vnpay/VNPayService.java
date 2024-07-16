package com.cookswp.milkstore.service.vnpay;

import com.cookswp.milkstore.pojo.dtos.PaymentModel.PaymentDTO;
import com.cookswp.milkstore.pojo.dtos.PaymentModel.RequestCallBack;
import com.cookswp.milkstore.pojo.dtos.PaymentModel.RequestPayment;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;

public interface VNPayService {

    public PaymentDTO createVNPayPayment(RequestPayment requestPayment, String orderID) throws ParseException;

    public PaymentDTO saveBillVNPayPayment(RequestCallBack requestCallBack) throws ParseException;
}
