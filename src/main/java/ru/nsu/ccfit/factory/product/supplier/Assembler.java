package ru.nsu.ccfit.factory.product.supplier;

import ru.nsu.ccfit.factory.IdGenerator;
import ru.nsu.ccfit.factory.product.Accessory;
import ru.nsu.ccfit.factory.product.Auto;
import ru.nsu.ccfit.factory.product.Body;
import ru.nsu.ccfit.factory.product.Engine;
import ru.nsu.ccfit.factory.storage.Storage;

public final class Assembler extends ProductSupplier<Auto> {
    private final Storage<Accessory> accessoryStorage;
    private final Storage<Body> bodyStorage;
    private final Storage<Engine> engineStorage;

    public Assembler(Storage<Accessory> accessoryStorage,
                     Storage<Body> bodyStorage,
                     Storage<Engine> engineStorage,
                     Storage<Auto> autoStorage,
                     int supplySpeed) {
        super(autoStorage, supplySpeed);
        this.accessoryStorage = accessoryStorage;
        this.bodyStorage = bodyStorage;
        this.engineStorage = engineStorage;
    }

    @Override
    protected Auto constructProduct() {
        try {
            return new Auto(
                    IdGenerator.getId(),
                    accessoryStorage.pollProduct(),
                    bodyStorage.pollProduct(),
                    engineStorage.pollProduct()
            );
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
