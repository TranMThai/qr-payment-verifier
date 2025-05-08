package com.qrpaymentverifier.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class SePayTransactionResponse {
    private String id;

    @JsonProperty("bank_brand_name")
    private String bankBrandName;

    @JsonProperty("account_number")
    private String accountNumber;

    @JsonProperty("transaction_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transactionDate;

    @JsonProperty("amount_out")
    private BigDecimal amountOut;

    @JsonProperty("amount_in")
    private BigDecimal amountIn;

    private BigDecimal accumulated;

    @JsonProperty("transaction_content")
    private String transactionContent;

    @JsonProperty("reference_number")
    private String referenceNumber;

    private String code;

    @JsonProperty("sub_account")
    private String subAccount;

    @JsonProperty("bank_account_id")
    private String bankAccountId;
}
