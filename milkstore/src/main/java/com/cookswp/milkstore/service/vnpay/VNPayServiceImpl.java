package com.cookswp.milkstore.service.vnpay;

import com.cookswp.milkstore.configuration.VNPayConfig;
import com.cookswp.milkstore.pojo.dtos.PaymentModel.PaymentDTO;
import com.cookswp.milkstore.pojo.dtos.PaymentModel.RequestCallBack;
import com.cookswp.milkstore.pojo.dtos.PaymentModel.RequestPayment;
import com.cookswp.milkstore.pojo.entities.TransactionLog;
import com.cookswp.milkstore.pojo.entities.User;
import com.cookswp.milkstore.repository.transactionLog.TransactionLogRepository;
import com.cookswp.milkstore.service.order.OrderService;
import com.cookswp.milkstore.service.user.UserService;
import com.cookswp.milkstore.utils.VNPayUtil;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class VNPayServiceImpl implements VNPayService {

    @Value("${payment.vnPay.url}")
    private String vnp_PayUrl;

    @Value("${payment.vnPay.returnUrl}")
    private String vnp_ReturnUrl;

    @Value("${payment.vnPay.tmnCode}")
    private String vnp_TmnCode;

    @Value("${payment.vnPay.secretKey}")
    private String secretKey;

    @Value("${payment.vnPay.version}")
    private String vnp_Version;

    @Value("${payment.vnPay.command}")
    private String vnp_Command;

    @Value("${payment.vnPay.orderType}")
    private String orderType;

    private final TransactionLogRepository transactionLogRepository;
    private final OrderService orderService;


    @Autowired
    public VNPayServiceImpl(TransactionLogRepository transactionLogRepository, OrderService orderService) {
        this.transactionLogRepository = transactionLogRepository;
        this.orderService = orderService;
    }

    @Override
    public PaymentDTO createVNPayPayment(RequestPayment requestPayment, String orderID) {
        long amount = requestPayment.getAmount() * 100L;
        Map<String, String> vnpParamsMap = new HashMap<>();
        vnpParamsMap.put("vnp_Version", this.vnp_Version);
        vnpParamsMap.put("vnp_Command", this.vnp_Command);
        vnpParamsMap.put("vnp_TmnCode", this.vnp_TmnCode);
        vnpParamsMap.put("vnp_CurrCode", "VND");
        vnpParamsMap.put("vnp_TxnRef", orderID);
        vnpParamsMap.put("vnp_OrderInfo", "Thanh toan don hang:" + VNPayUtil.getRandomNumber(8));
        vnpParamsMap.put("vnp_OrderType", this.orderType);
        vnpParamsMap.put("vnp_Locale", "vn");
        vnpParamsMap.put("vnp_ReturnUrl", this.vnp_ReturnUrl);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnpCreateDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_CreateDate", vnpCreateDate);
        calendar.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(calendar.getTime());
        vnpParamsMap.put("vnp_ExpireDate", vnp_ExpireDate);
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        String bankCode = requestPayment.getBankCode();
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", "0:0:0:0:0:0:0:1");
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(secretKey, hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentURL = vnp_PayUrl + "?" + queryUrl;
        return PaymentDTO.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentURL)
                .build();
    }

    @Transactional
    @Override
    public PaymentDTO saveBillVNPayPayment(RequestCallBack requestCallBack) {
        TransactionLog trans = TransactionLog.builder()
                .txnRef(requestCallBack.getTxnRef())
                .amount(Long.parseLong(String.valueOf(requestCallBack.getAmount())))
                .bankCode(requestCallBack.getBankCode())
                .bankTranNo(requestCallBack.getBankTranNo())
                .cardType(requestCallBack.getCardType())
                .orderInfo(requestCallBack.getOrderInfo())
                .responseCode(requestCallBack.getResponseCode())
                .payDate(VNPayUtil.convertPayDate(requestCallBack.getPayDate()))
                .transactionNo(requestCallBack.getTransactionNo())
                .transactionStatus(requestCallBack.getTransactionStatus())
                .build();
        transactionLogRepository.save(trans);
        String responseCode = requestCallBack.getResponseCode();
        String message = responseCode.equals("00") ? "Giao dịch thành công" : "Giao dịch thất bại";
        orderService.updateOrderStatus(trans.getTxnRef());
        return PaymentDTO.builder()
                .code(responseCode)
                .message(message)
                .build();
    }

}
