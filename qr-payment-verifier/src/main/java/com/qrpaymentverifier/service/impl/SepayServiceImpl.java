package com.qrpaymentverifier.service.impl;

import com.qrpaymentverifier.config.TransactionSyncScheduler;
import com.qrpaymentverifier.dto.response.BankAccountResponse;
import com.qrpaymentverifier.dto.response.SePayBankAccountResponse;
import com.qrpaymentverifier.dto.response.SePayTransactionResponse;
import com.qrpaymentverifier.mapper.BankAccountMapper;
import com.qrpaymentverifier.repository.TransactionRepository;
import com.qrpaymentverifier.repository.httpclient.SePayClient;
import com.qrpaymentverifier.service.SePayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SepayServiceImpl implements SePayService {

    @Value("${sepay.token}")
    private String sePayToken;
    private final SePayClient sePayClient;
    private final TransactionRepository transactionRepository;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private LocalDateTime minTime = null;

    @Override
    public List<SePayTransactionResponse> getSepayTransactions() {
        Map<String, String> params = new HashMap<>();
        params.put("transaction_date_min", getMinTime());
        List<SePayTransactionResponse> transactions = sePayClient.getTransactionList(sePayToken, params).getTransactions();
        Optional<SePayTransactionResponse> firstTransaction = transactions.stream().findFirst();
        firstTransaction.ifPresent(transaction -> {
            minTime = transaction.getTransactionDate();
        });
        return transactions;
    }

    @Override
    public BankAccountResponse getBankAccount() {
        Map<String,String> params = new HashMap<>();
        params.put("limit", "1");
        TransactionSyncScheduler.stopScheduled();
        List<SePayBankAccountResponse> bankAccounts = null;
        for(int i = 1; i<=5; i++){
            try{
                bankAccounts = sePayClient.getBankAccountList(sePayToken,params).getBankaccounts();
            }
            catch (Exception e){
                log.warn("Retry");
            }
            if(bankAccounts != null) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //TODO: throw exception
            }
        }
        TransactionSyncScheduler.continueScheduled();
        if(bankAccounts == null || bankAccounts.isEmpty()) {
            //TODO: throw exception
        }
        return BankAccountMapper.toBankAccountResponse(bankAccounts.getFirst());
    }

    private String getMinTime() {
        if (minTime == null) {
            minTime = transactionRepository.getOldestNotReadTransaction().getTransactionDate();
        }
        return formatter.format(minTime);
    }
}
