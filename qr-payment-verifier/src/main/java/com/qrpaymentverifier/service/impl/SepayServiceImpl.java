package com.qrpaymentverifier.service.impl;

import com.qrpaymentverifier.dto.response.SePayTransactionResponse;
import com.qrpaymentverifier.repository.TransactionRepository;
import com.qrpaymentverifier.repository.httpclient.SePayClient;
import com.qrpaymentverifier.service.SePayService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SepayServiceImpl implements SePayService {

    @Value("${sepay.token}")
    private String sePayToken;
    private final SePayClient sePayClient;
    private final TransactionRepository transactionRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private LocalDateTime minTime = null;

    @Override
    public List<SePayTransactionResponse> getSepayTransactions() {
        Map<String, String> params = new HashMap<>();
        params.put("transaction_date_min", getMinTime());
        List<SePayTransactionResponse> transactions = sePayClient.getTransactionList(sePayToken, params).getTransactions();
        Optional<SePayTransactionResponse> firstTransaction = transactions.stream().findFirst();
        firstTransaction.ifPresent(transaction -> {
            minTime = transaction.getTransactionDate();
        });
        return transactions;
    }

    private String getMinTime() {
        if (minTime == null) {
            minTime = transactionRepository.getOldestNotReadTransaction().getTransactionDate();
        }
        return formatter.format(minTime);
    }
}
