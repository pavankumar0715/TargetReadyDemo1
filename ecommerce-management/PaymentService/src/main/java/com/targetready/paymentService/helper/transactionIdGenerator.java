package com.targetready.paymentService.helper;

import java.util.UUID;

public class transactionIdGenerator {
    public static String generateTransactionId() {
        return UUID.randomUUID().toString();
    }

}
