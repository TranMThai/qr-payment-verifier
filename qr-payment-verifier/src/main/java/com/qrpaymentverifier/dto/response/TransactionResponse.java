package com.qrpaymentverifier.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponse {

    private String id;

    private BigDecimal amountIn;

    private String speech;

    private LocalDateTime transactionDate;

}
