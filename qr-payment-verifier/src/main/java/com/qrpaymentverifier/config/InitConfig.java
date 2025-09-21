package com.qrpaymentverifier.config;

import com.qrpaymentverifier.service.SePayService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitConfig {

    @Bean
    ApplicationRunner init(SePayService sePayService) {
        return args -> {
            sePayService.getSepayTransactions();
        };
    }

}
