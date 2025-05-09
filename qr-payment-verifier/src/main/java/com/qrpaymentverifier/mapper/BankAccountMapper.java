package com.qrpaymentverifier.mapper;

import com.qrpaymentverifier.dto.response.BankAccountResponse;
import com.qrpaymentverifier.dto.response.SePayBankAccountResponse;

public class BankAccountMapper {

    public static BankAccountResponse toBankAccountResponse(SePayBankAccountResponse sepayResponse) {
        return BankAccountResponse.builder()
                .accountNumber(sepayResponse.getAccountNumber())
                .bankShortName(sepayResponse.getBankShortName())
                .accountHolderName(sepayResponse.getAccountHolderName())
                .build();
    }
}
