package com.qrpaymentverifier.repository.httpclient;

import com.qrpaymentverifier.dto.response.SePayTransactionListResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "SePay", url = "https://my.sepay.vn/userapi/transactions")
public interface SePayClient {

    @GetMapping("/list")
     SePayTransactionListResponse getTransactionList(
            @RequestHeader(name = "Authorization") String token,
            @RequestParam Map<String, String> params
    );

}
