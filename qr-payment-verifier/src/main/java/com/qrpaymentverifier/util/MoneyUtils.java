package com.qrpaymentverifier.util;

import java.math.BigDecimal;

public class MoneyUtils {
    public static Number normalizeDecimal(BigDecimal amount) {
       Double number = amount.doubleValue();
       if(number%1==0){
           return number.intValue();
       }
       return number;
    }
}
