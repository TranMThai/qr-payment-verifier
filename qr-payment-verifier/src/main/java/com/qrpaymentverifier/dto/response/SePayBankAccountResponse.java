package com.qrpaymentverifier.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SePayBankAccountResponse {

    private String id;

    @JsonProperty("account_holder_name")
    private String accountHolderName;

    @JsonProperty("account_number")
    private String accountNumber;

    private String accumulated;

    @JsonProperty("last_transaction")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastTransaction;

    private String label;

    private String active;

    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonProperty("bank_short_name")
    private String bankShortName;

    @JsonProperty("bank_full_name")
    private String bankFullName;

    @JsonProperty("bank_bin")
    private String bankBin;

    @JsonProperty("bank_code")
    private String bankCode;
}
