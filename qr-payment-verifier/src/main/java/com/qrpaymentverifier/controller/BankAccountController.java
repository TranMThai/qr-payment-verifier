package com.qrpaymentverifier.controller;

import com.qrpaymentverifier.dto.response.ApiResponse;
import com.qrpaymentverifier.dto.response.BankAccountResponse;
import com.qrpaymentverifier.entity.Transaction;
import com.qrpaymentverifier.service.SePayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bank-account")
@RequiredArgsConstructor
public class BankAccountController {

    private final SePayService sePayService;

    @GetMapping
    public BankAccountResponse getBankAccount() {
        return sePayService.getBankAccount();
    }

}
