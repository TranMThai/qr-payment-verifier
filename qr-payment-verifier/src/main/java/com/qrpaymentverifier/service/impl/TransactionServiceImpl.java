package com.qrpaymentverifier.service.impl;

import com.qrpaymentverifier.dto.projection.DailyRevenueProjection;
import com.qrpaymentverifier.dto.request.SepayTransactionRequest;
import com.qrpaymentverifier.dto.response.TransactionResponse;
import com.qrpaymentverifier.entity.Transaction;
import com.qrpaymentverifier.mapper.TransactionMapper;
import com.qrpaymentverifier.repository.TransactionRepository;
import com.qrpaymentverifier.service.SePayService;
import com.qrpaymentverifier.service.TextToSpeechService;
import com.qrpaymentverifier.service.TransactionService;
import com.qrpaymentverifier.service.WebSocketService;
import com.qrpaymentverifier.util.MoneyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    private final SePayService sePayService;
    private final TextToSpeechService textToSpeechService;
    private final WebSocketService webSocketService;
    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> syncTransactions(boolean sendSocket) {

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
                .map(this::toResponse)
                .toList();

        if (!responses.isEmpty() && sendSocket) {
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

    @Override
    public void receiveTransaction(SepayTransactionRequest request) {
        Transaction transaction = Transaction.builder()
                .id(String.valueOf(request.getId()))
                .bankBrandName(request.getGateway())
                .accountNumber(request.getAccountNumber())
                .transactionDate(LocalDateTime.parse(request.getTransactionDate(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .amountIn("in".equalsIgnoreCase(request.getTransferType())
                        ? request.getTransferAmount()
                        : BigDecimal.ZERO)
                .amountOut("out".equalsIgnoreCase(request.getTransferType())
                        ? request.getTransferAmount()
                        : BigDecimal.ZERO)
                .accumulated(request.getAccumulated())
                .transactionContent(request.getContent())
                .referenceNumber(request.getReferenceCode())
                .code(request.getCode())
                .subAccount(request.getSubAccount())
                .bankAccountId(null)
                .isRead(false)
                .build();

        transactionRepository.save(transaction);
        webSocketService.responseRealtime(List.of(toResponse(transaction)));
    }

    @Override
    public List<TransactionResponse> getTransactionList(LocalDateTime date, Integer size) {
        Pageable pageable = PageRequest.ofSize(size);
        List<Transaction> transactions = transactionRepository.getTransactionsByDate(date, pageable);
        return transactions.stream()
                .map(entity -> TransactionMapper.toDto(entity, null))
                .toList();
    }

    @Override
    public DailyRevenueProjection getRevenueDaily() {
        LocalDate date = LocalDate.now();
        LocalDate yesterday = date.minusDays(1);
        DailyRevenueProjection dailyRevenue = transactionRepository.getRevenueDaily(yesterday);
        return dailyRevenue;
    }

    private TransactionResponse toResponse(Transaction entity) {
        Number amountIn = MoneyUtils.normalizeDecimal(entity.getAmountIn());
        String speech = Base64.getEncoder().encodeToString(textToSpeechService.synthesizeToBytes("Bạn đã nhận được " + amountIn + " đồng"));
        return TransactionMapper.toDto(entity, speech);
    }


}
