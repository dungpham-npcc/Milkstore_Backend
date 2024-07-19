package com.cookswp.milkstore.pojo.dtos.RefundModel;

import com.cookswp.milkstore.enums.RefundStatus;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefundDTO {

    private int id;
    private LocalDateTime requestTime;
    private int userId;
    private String senderName;
    private String senderAddress;
    private String senderPhone;
    private String productName;
    private String customerRefundReason;
    private String customerNote;
    private String staffNote;
    private String customerImage;
    private String staffRejectReason;
    private String staffRejectImage;
    private String staffReceivedImage;
    private RefundStatus refundStatus;

}
