package com.targetready.paymentService.producer;

public class PaymentGatewayTimeoutException extends RuntimeException {
    public PaymentGatewayTimeoutException(String message) {
        super(message);
    }
}
