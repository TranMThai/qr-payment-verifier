package com.qrpaymentverifier.service;


import com.qrpaymentverifier.dto.response.BankAccountResponse;
import com.qrpaymentverifier.dto.response.SePayBankAccountResponse;
import com.qrpaymentverifier.dto.response.SePayTransactionResponse;

import java.util.List;

public interface SePayService {
    List<SePayTransactionResponse> getSepayTransactions();
    BankAccountResponse getBankAccount();
}
