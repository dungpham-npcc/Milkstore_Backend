package com.cookswp.milkstore.pojo.entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transaction_log")
public class TransactionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "amount", nullable = false)
    private long amount;

    @Column(name = "bank_code", nullable = false)
    private String bankCode;

    @Column(name = "bank_tran_no", nullable = false)
    private String bankTranNo;

    @Column(name = "cart_type", nullable = false)
    private String cardType;

    @Column(name = "order_info", nullable = false)
    private String orderInfo;

    @Column(name = "response_code", nullable = false)
    private String responseCode;

    @Column(name = "pay_date", nullable = false)
    private String payDate;

    @Column(name = "transaction_no", nullable = false)
    private String transactionNo;

    @Column(name = "transaction_status", nullable = false)
    private String transactionStatus;

    @Column(name = "txn_ref", nullable = false, unique = true)
    private String txnRef;

    // Constructors, getters, setters, etc.
}
