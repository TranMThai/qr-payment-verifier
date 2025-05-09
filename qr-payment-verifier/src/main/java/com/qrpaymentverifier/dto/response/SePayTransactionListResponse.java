package com.qrpaymentverifier.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class SePayTransactionListResponse extends SePayResponse{
    private List<SePayTransactionResponse> transactions;
}
