package com.qrpaymentverifier.mapper;

import com.qrpaymentverifier.dto.response.SePayTransactionResponse;
import com.qrpaymentverifier.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public Transaction toEntity(SePayTransactionResponse dto) {
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
}
