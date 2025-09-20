package com.qrpaymentverifier.controller.webhook;

import com.qrpaymentverifier.dto.request.SepayTransactionRequest;
import com.qrpaymentverifier.exception.AppException;
import com.qrpaymentverifier.exception.ErrorCode;
import com.qrpaymentverifier.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hooks/sepay-payment")
@RequiredArgsConstructor
public class SepayHookController {

    @Value("${sepay.api-key:}")
    private String apiKey;

    private final TransactionService transactionService;

    @PostMapping
    public void receiveTransaction(@RequestBody SepayTransactionRequest request,
                                   @RequestHeader(value = "Authorization",required = false) String authHeader) {
        if(authHeader != null) {
            authHeader = authHeader.replace("Apikey ", "");
        }
        if (apiKey.equals("") || apiKey.equals(authHeader)) {
            transactionService.receiveTransaction(request);
        } else {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
    }

}
