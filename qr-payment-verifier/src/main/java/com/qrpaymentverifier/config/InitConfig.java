package com.qrpaymentverifier.config;

import com.qrpaymentverifier.service.SePayService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InitConfig {

    private final SePayService sePayService;

    @Bean
    ApplicationRunner init(TransactionSyncScheduler transactionSyncScheduler) {
        return args -> {
            sePayService.getSepayTransactions();
        };
    }

}
