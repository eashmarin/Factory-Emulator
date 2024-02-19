package ru.nsu.ccfit.factory;

import ru.nsu.ccfit.factory.product.Auto;
import ru.nsu.ccfit.factory.product.supplier.ProductSupplier;
import ru.nsu.ccfit.factory.storage.Storage;

import java.util.concurrent.ThreadPoolExecutor;

public class AutoController implements Runnable {

    private final ThreadPoolExecutor workersPool;
    private final Storage<Auto> storage;
    ProductSupplier<Auto> assembler;

    public AutoController(Storage<Auto> storage,
                          ThreadPoolExecutor workersPool,
                          ProductSupplier<Auto> assembler) {
        this.storage = storage;
        this.workersPool = workersPool;
        this.assembler = assembler;
    }

    @Override
    public synchronized void run() {
        while (true) {
            synchronized (storage) {
                if (storage.getSize() < workersPool.getCorePoolSize()) {
                    for (int i = 0; i < workersPool.getCorePoolSize() - storage.getSize(); i++) {
                        workersPool.submit(assembler);
                    }
                    storage.notifyAll();
                }
            }
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
