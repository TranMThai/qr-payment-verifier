package com.qrpaymentverifier.scheduler;

import com.qrpaymentverifier.config.condition.MailCondition;
import com.qrpaymentverifier.dto.projection.DailyRevenueProjection;
import com.qrpaymentverifier.service.MailService;
import com.qrpaymentverifier.service.TransactionService;
import com.qrpaymentverifier.util.MoneyUtils;
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
        String subject = "Báo cáo doanh thu " + yesterdayStr;

        //Content
        DailyRevenueProjection dailyRevenue = transactionService.getRevenueDaily();
        String revenue = MoneyUtils.normalizeDecimal(dailyRevenue.getRevenue()).toString();
        int count = dailyRevenue.getCount();
        String content = """
                        <html>
                          <body style="font-family: Arial, sans-serif; background-color:#f4f6f7; margin:0; padding:20px;">
                            <div style="max-width:600px; margin:auto; background:#ffffff; border-radius:12px; overflow:hidden; box-shadow:0 4px 12px rgba(0,0,0,0.1);">
                
                              <div style="background:#2E86C1; color:#ffffff; padding:20px 24px; text-align:center;">
                                <h2 style="margin:0;">Báo cáo doanh thu</h2>
                              </div>
                
                              <div style="padding:24px; text-align:center;">
                                <p style="margin:0 0 20px;">Ngày báo cáo: <strong>%s</strong></p>
                
                                <div style="margin-bottom:16px; padding:20px; border-radius:8px; background:#E8F8F5;">
                                  <div style="font-size:14px; color:#117A65; margin-bottom:8px;">Tổng doanh thu</div>
                                  <div style="font-size:22px; font-weight:bold; color:#28B463;">%s VND</div>
                                </div>
                
                                <div style="padding:20px; border-radius:8px; background:#EBF5FB;">
                                  <div style="font-size:14px; color:#1F618D; margin-bottom:8px;">Số giao dịch</div>
                                  <div style="font-size:20px; font-weight:bold; color:#2E86C1;">%d</div>
                                </div>
                              </div>
                            </div>
                          </body>
                        </html>
                """.formatted(yesterdayStr, revenue, count);

        //Gửi mail
        log.info("Đang gửi mail");
        mailService.sendMail(toUserName, subject, content);
        log.info("Đã gửi mail");
    }

}
