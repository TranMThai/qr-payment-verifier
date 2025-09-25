package com.qrpaymentverifier.scheduler;

import com.qrpaymentverifier.config.condition.MailCondition;
import com.qrpaymentverifier.service.MailService;
import com.qrpaymentverifier.service.TransactionService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
@Conditional(MailCondition.class)
@Slf4j
public class NotifyMailScheduler {

    @Value("${spring.mail.username}")
    private String toUserName;

    private final MailService mailService;
    private final TransactionService transactionService;

    @Scheduled(cron = "10 0 0 * * ?")
    public void notifyMail() {
        log.info("Đang xử lý mail");
        //Subject
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        String yesterdayStr = yesterday.format(formatter);
        String subject = "Báo cáo doanh thu "+yesterdayStr;

        //Content
        Number revenue = transactionService.getRevenueDaily();
        String content = """
        <html>
        <body style="font-family: Arial, sans-serif; background-color:#f9f9f9; padding:20px;">
            <div style="max-width:600px; margin:auto; background-color:#ffffff; padding:20px; border-radius:8px; box-shadow:0 0 10px rgba(0,0,0,0.1);">
                <h2 style="color:#2E86C1;">Báo cáo doanh thu</h2>
                <p>Ngày: <strong>%s</strong></p>
                <p>Doanh thu hôm qua: <strong style="color:#28B463; font-size:18px;">%,d VND</strong></p>
            </div>
        </body>
        </html>
        """.formatted(yesterdayStr, revenue.longValue());

        //Gửi mail
        log.info("Đang gửi mail");
        mailService.sendMail(toUserName,subject,content);
        log.info("Đã gửi mail");
    }

}
