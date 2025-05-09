package com.qrpaymentverifier.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class SePayResponse {
    private int status;
    private Object error;
    private Object message;
}
