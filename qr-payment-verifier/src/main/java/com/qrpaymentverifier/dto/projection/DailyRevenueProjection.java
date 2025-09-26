package com.qrpaymentverifier.dto.projection;

import java.math.BigDecimal;

public interface DailyRevenueProjection {
    BigDecimal getRevenue();
    int getCount();
}
