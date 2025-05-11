package com.qrpaymentverifier.service.impl;

import com.google.cloud.texttospeech.v1.AudioConfig;
import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SynthesisInput;
import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams;
import com.google.protobuf.ByteString;
import com.qrpaymentverifier.dto.response.SePayTransactionResponse;
import com.qrpaymentverifier.repository.TransactionRepository;
import com.qrpaymentverifier.repository.httpclient.SePayClient;
import com.qrpaymentverifier.service.SePayService;
import com.qrpaymentverifier.service.TextToSpeechService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TextToSpeechServiceImpl implements TextToSpeechService {

    private final TextToSpeechClient ttsClient;

    @Override
    public byte[] synthesizeToBytes(String text) {
        SynthesisInput input = SynthesisInput.newBuilder().setText(text).build();

        VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
                .setLanguageCode("vi-VN")
                .setName("vi-VN-Chirp3-HD-Zephyr")
                .build();

        AudioConfig audioConfig = AudioConfig.newBuilder()
                .setAudioEncoding(AudioEncoding.LINEAR16)
                .build();

        SynthesizeSpeechResponse response = ttsClient.synthesizeSpeech(input, voice, audioConfig);
        return response.getAudioContent().toByteArray();
    }
}
