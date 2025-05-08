package com.qrpaymentverifier.config;

import com.qrpaymentverifier.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionSyncScheduler {

    private final TransactionService transactionService;

    @Scheduled(fixedRate = 1000)
    public void syncTransactions() {
        transactionService.syncTransactions();
    }

}
