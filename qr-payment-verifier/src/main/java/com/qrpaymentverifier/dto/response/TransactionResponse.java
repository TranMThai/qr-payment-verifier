package com.qrpaymentverifier.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class TransactionResponse {

    private String id;

    private BigDecimal amountIn;

    private byte[] speech;

}
