package com.qrpaymentverifier.controller;

import com.qrpaymentverifier.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final TransactionService transactionService;

    @MessageMapping("/read")
    @SendTo("/notification")
    public void sendMessage(String id) {
        transactionService.readTransactions(id);
    }

}
