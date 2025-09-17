package com.qrpaymentverifier.config;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class NgrokManager {

    @Value("${ngrok.url:}")
    private String url;

    @Value("${server.port}")
    private String port;

    private Process process;

    @PostConstruct
    public void startNgrok() throws InterruptedException {
        if(url == null || url.isEmpty()) {
            return;
        }
        ProcessBuilder processBuilder = new ProcessBuilder("ngrok","http","--url="+url,port);
        try {
            processBuilder.inheritIO();
            process = processBuilder.start();
            log.info("Ngrok started");
        }catch (IOException e) {
            log.info("Ngrok fail to start");
        }
    }

    @PreDestroy
    public void stopNgrok() {
        if(process != null && process.isAlive()) {
            process.destroy();
            log.info("Ngrok stopped");
        }
    }

}