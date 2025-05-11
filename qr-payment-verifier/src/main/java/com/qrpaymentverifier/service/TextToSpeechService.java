package com.qrpaymentverifier.service;

public interface TextToSpeechService {
    byte[] synthesizeToBytes(String text);
}
