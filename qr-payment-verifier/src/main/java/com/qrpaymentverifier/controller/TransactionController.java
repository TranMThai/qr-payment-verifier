package com.qrpaymentverifier.controller;

import com.qrpaymentverifier.dto.request.TransactionListRequest;
import com.qrpaymentverifier.dto.response.TransactionResponse;
import com.qrpaymentverifier.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/list")
    public List<TransactionResponse> readTransactions(TransactionListRequest request) {
       return transactionService.getTransactionList(request);
    }

}
