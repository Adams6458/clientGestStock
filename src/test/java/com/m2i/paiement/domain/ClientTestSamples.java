package com.m2i.paiement.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ClientTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Client getClientSample1() {
        return new Client()
            .id(1L)
            .codeAgence("codeAgence1")
            .telephone("telephone1")
            .nom("nom1")
            .prenom("prenom1")
            .sexe("sexe1")
            .titre("titre1")
            .piece("piece1")
            .reference("reference1")
            .autoriteDelivre("autoriteDelivre1")
            .typeClient("typeClient1");
    }

    public static Client getClientSample2() {
        return new Client()
            .id(2L)
            .codeAgence("codeAgence2")
            .telephone("telephone2")
            .nom("nom2")
            .prenom("prenom2")
            .sexe("sexe2")
            .titre("titre2")
            .piece("piece2")
            .reference("reference2")
            .autoriteDelivre("autoriteDelivre2")
            .typeClient("typeClient2");
    }

    public static Client getClientRandomSampleGenerator() {
        return new Client()
            .id(longCount.incrementAndGet())
            .codeAgence(UUID.randomUUID().toString())
            .telephone(UUID.randomUUID().toString())
            .nom(UUID.randomUUID().toString())
            .prenom(UUID.randomUUID().toString())
            .sexe(UUID.randomUUID().toString())
            .titre(UUID.randomUUID().toString())
            .piece(UUID.randomUUID().toString())
            .reference(UUID.randomUUID().toString())
            .autoriteDelivre(UUID.randomUUID().toString())
            .typeClient(UUID.randomUUID().toString());
    }
}
