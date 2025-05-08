package com.qrpaymentverifier.controller;

import com.qrpaymentverifier.service.TextToSpeechService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/speak")
@RequiredArgsConstructor
public class TTSController {

    private final TextToSpeechService ttsService;

    @GetMapping("")
    public ResponseEntity<byte[]> speak(@RequestParam(name = "text") String text) throws Exception {
        byte[] audioBytes = ttsService.synthesizeToBytes(text);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"speech.wav\"");

        return new ResponseEntity<>(audioBytes, headers, HttpStatus.OK);
    }
}
