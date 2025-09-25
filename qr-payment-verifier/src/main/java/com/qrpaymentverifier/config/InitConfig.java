package com.qrpaymentverifier.config;

import com.qrpaymentverifier.dto.response.SePayTransactionResponse;
import com.qrpaymentverifier.dto.response.TransactionResponse;
import com.qrpaymentverifier.service.SePayService;
import com.qrpaymentverifier.service.TransactionService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class InitConfig {

    @Bean
    ApplicationRunner init(TransactionService transactionService) {
        return args -> {
            transactionService.syncTransactions(false);
        };
    }

}
