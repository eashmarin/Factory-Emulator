package ru.nsu.ccfit.factory.product.supplier;

import ru.nsu.ccfit.factory.IdGenerator;
import ru.nsu.ccfit.factory.product.Body;
import ru.nsu.ccfit.factory.storage.Storage;

public final class BodySupplier extends ProductSupplier<Body> {

    public BodySupplier(Storage<Body> storage, int supplySpeed) {
        super(storage, supplySpeed);
    }

    @Override
    protected Body constructProduct() {
        return new Body(IdGenerator.getId());
    }
}
