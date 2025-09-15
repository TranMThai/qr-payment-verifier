package com.qrpaymentverifier.service;

import com.qrpaymentverifier.dto.request.SepayTransactionRequest;
import com.qrpaymentverifier.entity.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> syncTransactions();
    void readTransactions(String id);
    void receiveTransaction(SepayTransactionRequest transactions);
}
