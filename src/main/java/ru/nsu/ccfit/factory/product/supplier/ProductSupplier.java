package ru.nsu.ccfit.factory.product.supplier;

import ru.nsu.ccfit.factory.product.Product;
import ru.nsu.ccfit.factory.storage.Storage;

public abstract sealed class ProductSupplier<T extends Product> implements Runnable
        permits AccessorySupplier, BodySupplier, EngineSupplier, Assembler {
    protected final Storage<T> storage;
    protected int supplySpeed;

    public ProductSupplier(Storage<T> storage, int supplySpeed) {
        this.storage = storage;
        this.supplySpeed = supplySpeed;
    }

    public final void supply() {
        try {
            storage.addProduct(constructProduct());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract T constructProduct();

    @Override
    public final void run() {
        while (true) {
            try {
                Thread.sleep(supplySpeed);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            supply();
        }
    }
}
