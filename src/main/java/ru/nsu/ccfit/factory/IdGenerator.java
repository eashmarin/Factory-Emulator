package ru.nsu.ccfit.factory;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private final static AtomicInteger id = new AtomicInteger(0);

    public static int getId() {
        return id.getAndIncrement();
    }
}
