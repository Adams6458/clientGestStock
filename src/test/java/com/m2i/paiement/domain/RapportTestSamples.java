package com.m2i.paiement.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RapportTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Rapport getRapportSample1() {
        return new Rapport()
            .id(1L)
            .userID("userID1")
            .message("message1")
            .codeErr("codeErr1")
            .textExpl("textExpl1")
            .errcode("errcode1")
            .transactionId("transactionId1");
    }

    public static Rapport getRapportSample2() {
        return new Rapport()
            .id(2L)
            .userID("userID2")
            .message("message2")
            .codeErr("codeErr2")
            .textExpl("textExpl2")
            .errcode("errcode2")
            .transactionId("transactionId2");
    }

    public static Rapport getRapportRandomSampleGenerator() {
        return new Rapport()
            .id(longCount.incrementAndGet())
            .userID(UUID.randomUUID().toString())
            .message(UUID.randomUUID().toString())
            .codeErr(UUID.randomUUID().toString())
            .textExpl(UUID.randomUUID().toString())
            .errcode(UUID.randomUUID().toString())
            .transactionId(UUID.randomUUID().toString());
    }
}
