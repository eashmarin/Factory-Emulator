package ru.nsu.ccfit.factory.product.supplier;

import ru.nsu.ccfit.factory.IdGenerator;
import ru.nsu.ccfit.factory.product.Accessory;
import ru.nsu.ccfit.factory.storage.Storage;

public final class AccessorySupplier extends ProductSupplier<Accessory> {

    public AccessorySupplier(Storage<Accessory> storage, int supplySpeed) {
        super(storage, supplySpeed);
    }

    @Override
    protected Accessory constructProduct() {
        return new Accessory(IdGenerator.getId());
    }
}
