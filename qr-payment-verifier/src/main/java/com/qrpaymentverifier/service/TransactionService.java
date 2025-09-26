package com.qrpaymentverifier.service;

import com.qrpaymentverifier.dto.projection.DailyRevenueProjection;
import com.qrpaymentverifier.dto.request.SepayTransactionRequest;
import com.qrpaymentverifier.dto.response.TransactionResponse;
import com.qrpaymentverifier.entity.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    List<Transaction> syncTransactions(boolean sendSocket);
    void readTransactions(String id);
    void receiveTransaction(SepayTransactionRequest transactions);
    List<TransactionResponse> getTransactionList(LocalDateTime date, Integer size);
    DailyRevenueProjection getRevenueDaily();
}
