package com.qrpaymentverifier.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.TextToSpeechSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

@Configuration
public class GoogleTTSConfig {

    @Value("${google.tts.credentials.path}")
    private String credentialsPath;

    @Bean
    public TextToSpeechClient textToSpeechClient() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream(credentialsPath.replace("classpath:", ""));
        GoogleCredentials credentials = GoogleCredentials.fromStream(is);
        TextToSpeechSettings settings = TextToSpeechSettings.newBuilder()
                .setCredentialsProvider(() -> credentials)
                .build();
        return TextToSpeechClient.create(settings);
    }
}