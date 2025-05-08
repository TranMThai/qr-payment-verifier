package com.qrpaymentverifier.controller;

import com.qrpaymentverifier.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    @GetMapping
    public List<Transaction> getTransactions() {
        return null;
    }

}
