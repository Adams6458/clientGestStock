package com.m2i.paiement.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TransactionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Transaction getTransactionSample1() {
        return new Transaction().id(1L).userID("userID1").code("code1").message("message1").transactionId("transactionId1");
    }

    public static Transaction getTransactionSample2() {
        return new Transaction().id(2L).userID("userID2").code("code2").message("message2").transactionId("transactionId2");
    }

    public static Transaction getTransactionRandomSampleGenerator() {
        return new Transaction()
            .id(longCount.incrementAndGet())
            .userID(UUID.randomUUID().toString())
            .code(UUID.randomUUID().toString())
            .message(UUID.randomUUID().toString())
            .transactionId(UUID.randomUUID().toString());
    }
}
