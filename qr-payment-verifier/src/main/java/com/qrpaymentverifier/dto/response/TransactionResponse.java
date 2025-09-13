package com.qrpaymentverifier.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
public class TransactionResponse {

    private String id;

    private BigDecimal amountIn;

    private String speech;

    private LocalDateTime transactionDate;

}
