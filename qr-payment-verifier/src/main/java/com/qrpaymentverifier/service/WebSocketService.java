package com.qrpaymentverifier.service;

public interface WebSocketService {
    <T> void responseRealtime(T responses);
}
