package ru.nsu.ccfit.factory;

import ru.nsu.ccfit.factory.product.Auto;
import ru.nsu.ccfit.factory.storage.Storage;

import java.util.Calendar;

public class Dealer implements Runnable {
    private final Storage<Auto> autoStorage;
    private final AutoController autoController;

    public Dealer(Storage<Auto> autoStorage, AutoController autoController) {
        this.autoStorage = autoStorage;
        this.autoController = autoController;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Auto auto = autoStorage.pollProduct();
                synchronized (autoController) {
                    autoController.notify();
                }
                System.out.println(Calendar.getInstance().getTime() + ": Dealer " + Thread.currentThread().threadId() + ": " + auto);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
