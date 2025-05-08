package com.qrpaymentverifier.dto.response;

import com.qrpaymentverifier.entity.Transaction;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class SePayTransactionListResponse {
    private int status;
    private Object error;
    private Object message;
    private List<SePayTransactionResponse> transactions;
}
