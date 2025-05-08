package com.qrpaymentverifier.service.impl;

import com.qrpaymentverifier.config.WebSocketConfig;
import com.qrpaymentverifier.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketServiceImpl implements WebSocketService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public <T> void responseRealtime(T responses) {
        try {
            simpMessagingTemplate.convertAndSend(WebSocketConfig.WEBSOCKET_URL, responses);
        } catch (MessageDeliveryException e) {
        }
    }
}
