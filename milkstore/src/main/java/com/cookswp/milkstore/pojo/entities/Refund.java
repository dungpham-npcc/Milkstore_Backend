package com.cookswp.milkstore.pojo.entities;


import com.cookswp.milkstore.enums.RefundStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "refund_request")
public class Refund implements Serializable {

    @Id
    @Column(name = "refund_id")
    private int id;

    @Column(name = "request_time", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestTime;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "sender_name", nullable = false)
    private String senderName;

    @Column(name = "seder_phone", nullable = false)
    private String senderPhone;

    @Column(name = "sender_address", nullable = false)
    private String senderAddress;

    @Column(name = "prodcut_name", nullable = false)
    private String productName;

    @Column(name = "customer_refund_reason", nullable = false)
    private String customerRefundReason;

    @Column(name = "customer_note", nullable = true)
    private String customerNote;

    @Column(name = "staff_note", nullable = false)
    private String staffNote;

    @Column(name = "customer_image", nullable = false)
    private String customerImage;

    @Column(name = "staff_reject_reason", nullable = false)
    private String staffRejectReason;

    @Column(name = "staff_reject_image", nullable = false)
    private String staffRejectImage;

    @Column(name = "staff_received_image", nullable = false)
    private String staffReceivedImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "refund_request_status", nullable = false)
    private RefundStatus refundStatus;

}
