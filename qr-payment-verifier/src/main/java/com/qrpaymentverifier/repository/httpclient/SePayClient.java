package com.qrpaymentverifier.repository.httpclient;

import com.qrpaymentverifier.dto.response.SePayBankAccountListResponse;
import com.qrpaymentverifier.dto.response.SePayTransactionListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "SePay", url = "https://my.sepay.vn/userapi")
public interface SePayClient {

    @GetMapping("/transactions/list")
    SePayTransactionListResponse getTransactionList(
            @RequestHeader(name = "Authorization") String token,
            @RequestParam Map<String, String> params
    );

    @GetMapping("/bankaccounts/list")
    SePayBankAccountListResponse getBankAccountList(
            @RequestHeader(name = "Authorization") String token,
            @RequestParam Map<String, String> params
    );

}
