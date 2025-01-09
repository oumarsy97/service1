package com.users.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class LegumeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Legume getLegumeSample1() {
        return new Legume().id(1L).libelle("libelle1").quantite(1);
    }

    public static Legume getLegumeSample2() {
        return new Legume().id(2L).libelle("libelle2").quantite(2);
    }

    public static Legume getLegumeRandomSampleGenerator() {
        return new Legume().id(longCount.incrementAndGet()).libelle(UUID.randomUUID().toString()).quantite(intCount.incrementAndGet());
    }
}
