package com.qrpaymentverifier.config;

import com.qrpaymentverifier.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "server.webhook-enabled", havingValue = "false")
public class TransactionSyncScheduler {

    private final TransactionService transactionService;
    private static volatile boolean continueScheduled = true;

    @Scheduled(fixedRate = 2000)
    public void syncTransactions() throws InterruptedException {
        if (!continueScheduled) {
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
