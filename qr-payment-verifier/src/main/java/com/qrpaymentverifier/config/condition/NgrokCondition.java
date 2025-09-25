package com.qrpaymentverifier.config.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class NgrokCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String url = context.getEnvironment().getProperty("ngrok.url");
        return url == null || url.isBlank();
    }
}
