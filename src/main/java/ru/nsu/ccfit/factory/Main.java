package ru.nsu.ccfit.factory;

import ru.nsu.ccfit.factory.config.Parameter;
import ru.nsu.ccfit.factory.config.ParametersExtractor;
import ru.nsu.ccfit.factory.product.Accessory;
import ru.nsu.ccfit.factory.product.Auto;
import ru.nsu.ccfit.factory.product.Body;
import ru.nsu.ccfit.factory.product.Engine;
import ru.nsu.ccfit.factory.product.supplier.*;
import ru.nsu.ccfit.factory.storage.Storage;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws IOException {
        ParametersExtractor extractor = new ParametersExtractor();

        int DEALERS_NUMBER = Integer.parseInt(extractor.getParameter(Parameter.DEALERS_NUMBER));
        int WORKERS_NUMBER = Integer.parseInt(extractor.getParameter(Parameter.WORKERS_NUMBER));
        int ACCESSORY_SUPPLIERS_NUMBER = Integer.parseInt(extractor.getParameter(Parameter.ACCESSORY_SUPPLIERS_NUMBER));
        int ACCESSORY_SUPPLY_SPEED = Integer.parseInt(extractor.getParameter(Parameter.ACCESSORY_SUPPLY_SPEED));
        int BODY_SUPPLY_SPEED = Integer.parseInt(extractor.getParameter(Parameter.BODY_SUPPLY_SPEED));
        int ENGINE_SUPPLY_SPEED = Integer.parseInt(extractor.getParameter(Parameter.ENGINE_SUPPLY_SPEED));
        int AUTO_SUPPLY_SPEED = Integer.parseInt(extractor.getParameter(Parameter.AUTO_SUPPLY_SPEED));

        Storage<Accessory> accessoryStorage = new Storage<>(100);
        Storage<Body> bodyStorage = new Storage<>(100);
        Storage<Engine> engineStorage = new Storage<>(100);
        Storage<Auto> autoStorage = new Storage<>(100);

        ProductSupplier<Accessory> accessorySupplier = new AccessorySupplier(accessoryStorage, ACCESSORY_SUPPLY_SPEED);
        ProductSupplier<Body> bodySupplier = new BodySupplier(bodyStorage, BODY_SUPPLY_SPEED);
        ProductSupplier<Engine> engineSupplier = new EngineSupplier(engineStorage, ENGINE_SUPPLY_SPEED);
        ProductSupplier<Auto> assembler = new Assembler(
                accessoryStorage,
                bodyStorage,
                engineStorage,
                autoStorage,
                AUTO_SUPPLY_SPEED
        );

        try (ThreadPoolExecutor workersPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(WORKERS_NUMBER);
             ExecutorService dealersPool = Executors.newFixedThreadPool(DEALERS_NUMBER);
             ExecutorService accessorySuppliersPool = Executors.newFixedThreadPool(ACCESSORY_SUPPLIERS_NUMBER)
        ) {
            Thread bodySupplierThread = new Thread(bodySupplier);
            Thread engineSupplierThread = new Thread(engineSupplier);

            IntStream.range(0, DEALERS_NUMBER).forEach(v -> accessorySuppliersPool.submit(accessorySupplier));
            bodySupplierThread.start();
            engineSupplierThread.start();

            AutoController autoController = new AutoController(autoStorage, workersPool, assembler);

            Thread controllerThread = new Thread(autoController);
            controllerThread.start();

            IntStream.range(0, DEALERS_NUMBER).forEach(v -> dealersPool.submit(new Dealer(autoStorage, autoController)));
        }
    }
}