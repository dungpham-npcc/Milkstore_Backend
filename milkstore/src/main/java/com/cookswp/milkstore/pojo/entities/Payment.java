package com.cookswp.milkstore.pojo.entities;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @Column(name = "amount")
    Long amount;
    @Column(name = "bank_code")
    String bankCode;
    @Column(name = "bank_tran_no")
    String bankTranNo;
    @Column(name = "cart_type")
    String cardType;
    @Column(name = "order_info")
    String orderInfo;
    @Column(name = "response_code")
    String responseCode;
    @Column(name= "payDate")
    String payDate;
    @Column(name = "transaction_no")
    String transactionNo;
    @Column(name = "transaction_status")
    String transactionStatus;

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", amount=" + amount +
                ", bankCode='" + bankCode + '\'' +
                ", bankTranNo='" + bankTranNo + '\'' +
                ", cardType='" + cardType + '\'' +
                ", orderInfo='" + orderInfo + '\'' +
                ", responseCode='" + responseCode + '\'' +
                ", payDate=" + payDate +
                ", transactionNo='" + transactionNo + '\'' +
                ", transactionStatus='" + transactionStatus + '\'' +
                '}';
    }
}