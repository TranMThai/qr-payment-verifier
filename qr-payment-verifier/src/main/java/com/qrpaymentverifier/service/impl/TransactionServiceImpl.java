package com.qrpaymentverifier.service.impl;

import com.qrpaymentverifier.dto.response.SePayTransactionResponse;
import com.qrpaymentverifier.dto.response.TransactionResponse;
import com.qrpaymentverifier.entity.Transaction;
import com.qrpaymentverifier.mapper.TransactionMapper;
import com.qrpaymentverifier.repository.TransactionRepository;
import com.qrpaymentverifier.repository.httpclient.SePayClient;
import com.qrpaymentverifier.service.SePayService;
import com.qrpaymentverifier.service.TextToSpeechService;
import com.qrpaymentverifier.service.TransactionService;
import com.qrpaymentverifier.service.WebSocketService;
import com.qrpaymentverifier.util.MoneyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final SePayService sePayService;
    private final TextToSpeechService textToSpeechService;
    private final WebSocketService webSocketService;
    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> syncTransactions() {

        List<Transaction> newSePayTransaction = sePayService.getSepayTransactions().stream()
                .map(TransactionMapper::toEntity)
                .toList();

        List<Transaction> transactions = newSePayTransaction.stream()
                .filter(transaction ->
                        !transactionRepository.existsById(transaction.getId())
                        && transaction.getAmountIn().compareTo(BigDecimal.ZERO) > 0
                )
                .toList();

        List<TransactionResponse> responses = transactions.stream()
                .map(entity -> {
                    Number amountIn = MoneyUtils.normalizeDecimal(entity.getAmountIn());
                    byte[] speech = textToSpeechService.synthesizeToBytes("Thanh toán thành công "+amountIn+" đồng");
                    return TransactionMapper.toDto(entity, speech);
                })
                .toList();

        if(!responses.isEmpty()) {
            webSocketService.responseRealtime(responses);
        }

        return transactionRepository.saveAll(transactions);
    }

    @Override
    public void readTransactions(String id) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if (transactionOptional.isPresent()) {
            Transaction transaction = transactionOptional.get();
            transaction.setIsRead(true);
            transactionRepository.save(transaction);
        }
    }
}
