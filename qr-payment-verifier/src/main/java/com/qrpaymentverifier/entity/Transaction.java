package com.qrpaymentverifier.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transaction")
@Entity
public class Transaction {
    @Id
    private String id;

    @Column(name = "bank_brand_name")
    private String bankBrandName;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column(name = "amount_out")
    private BigDecimal amountOut;

    @Column(name = "amount_in")
    private BigDecimal amountIn;

    private BigDecimal accumulated;

    @Column(name = "transaction_content", columnDefinition = "TEXT")
    private String transactionContent;

    @Column(name = "reference_number")
    private String referenceNumber;

    private String code;

    @Column(name = "sub_account")
    private String subAccount;

    @Column(name = "bank_account_id")
    private String bankAccountId;

    @Column(name = "is_read")
    private Boolean isRead;

}
