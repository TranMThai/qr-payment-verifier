package com.qrpaymentverifier.config.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class MailCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String username = context.getEnvironment().getProperty("spring.mail.username");
        return username != null && !username.isBlank();
    }
}
