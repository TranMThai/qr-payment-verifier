package com.qrpaymentverifier.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BankAccountResponse {
    private String accountNumber;
    private String bankShortName;
    private String accountHolderName;
}
