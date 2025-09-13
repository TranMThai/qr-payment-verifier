package com.qrpaymentverifier.mapper;

import com.qrpaymentverifier.dto.response.SePayTransactionResponse;
import com.qrpaymentverifier.dto.response.TransactionResponse;
import com.qrpaymentverifier.entity.Transaction;

public class TransactionMapper {

    public static Transaction toEntity(SePayTransactionResponse dto) {
        return Transaction.builder()
                .id(dto.getId())
                .bankBrandName(dto.getBankBrandName())
                .accountNumber(dto.getAccountNumber())
                .transactionDate(dto.getTransactionDate())
                .amountOut(dto.getAmountOut())
                .amountIn(dto.getAmountIn())
                .accumulated(dto.getAccumulated())
                .transactionContent(dto.getTransactionContent())
                .referenceNumber(dto.getReferenceNumber())
                .code(dto.getCode())
                .subAccount(dto.getSubAccount())
                .bankAccountId(dto.getBankAccountId())
                .isRead(false)
                .build();
    }

    public static TransactionResponse toDto(Transaction entity, String speech) {
        return TransactionResponse.builder()
                .id(entity.getId())
                .amountIn(entity.getAmountIn())
                .transactionDate(entity.getTransactionDate())
                .speech(speech)
                .build();
    }
}
