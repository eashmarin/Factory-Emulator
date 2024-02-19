package ru.nsu.ccfit.factory.product.supplier;

import ru.nsu.ccfit.factory.IdGenerator;
import ru.nsu.ccfit.factory.product.Engine;
import ru.nsu.ccfit.factory.storage.Storage;

public final class EngineSupplier extends ProductSupplier<Engine> {

    public EngineSupplier(Storage<Engine> storage, int supplySpeed) {
        super(storage, supplySpeed);
    }

    @Override
    protected Engine constructProduct() {
        return new Engine(IdGenerator.getId());
    }
}
