package com.qrpaymentverifier.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    RUNTIME_EXCEPTION(400,"runtime exception", HttpStatus.BAD_REQUEST),
    UNAUTHORIZED(401,"Unauthorized", HttpStatus.UNAUTHORIZED),
    SEPAY_TOKEN_INVALID(401,"Sepay token invalid", HttpStatus.UNAUTHORIZED),
    NOT_FOUND_BANK_ACCOUNT(404,"Bank account not found. Please register your bank account in Sepay first.", HttpStatus.NOT_FOUND),
    SEPAY_SERVICE_UNAVAILABLE(503,"Unable to connect to Sepay service. Please try again later.",HttpStatus.SERVICE_UNAVAILABLE),
    SEPAY_SERVICE_TOO_MANY_REQUEST(429,"Too many requests to Sepay service. Please try again later.",HttpStatus.TOO_MANY_REQUESTS)
    ;
    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;
}
