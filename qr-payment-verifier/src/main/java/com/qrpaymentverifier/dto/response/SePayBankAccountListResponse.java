package com.qrpaymentverifier.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class SePayBankAccountListResponse extends SePayResponse {
    private List<SePayBankAccountResponse> bankaccounts;
}
