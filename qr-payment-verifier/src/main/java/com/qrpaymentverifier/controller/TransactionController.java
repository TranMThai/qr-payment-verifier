package com.qrpaymentverifier.controller;

import com.qrpaymentverifier.dto.response.TransactionResponse;
import com.qrpaymentverifier.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/list")
    public List<TransactionResponse> readTransactions(@RequestParam(required = false) LocalDateTime date,
                                                      @RequestParam Integer size) {
        return transactionService.getTransactionList(date, size);
    }

}
