package com.qrpaymentverifier.config;

import com.qrpaymentverifier.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionSyncScheduler {

    private final TransactionService transactionService;
    private static volatile boolean continueScheduled = true;

    @Scheduled(fixedRate = 5000)
    public void syncTransactions() throws InterruptedException {
        if(!continueScheduled) {
            Thread.sleep(1000);
            return;
        }
        transactionService.syncTransactions();
    }

    public static void stopScheduled() {
        continueScheduled = false;
    }

    public static void continueScheduled() {
        continueScheduled = true;
    }

}
