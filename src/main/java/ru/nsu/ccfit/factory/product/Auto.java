package ru.nsu.ccfit.factory.product;

public final class Auto extends Product {
    private final Accessory accessory;
    private final Body body;
    private final Engine engine;

    public Auto(int id,
                Accessory accessory,
                Body body,
                Engine engine) {
        super(id);
        this.accessory = accessory;
        this.body = body;
        this.engine = engine;
    }

    public Accessory getAccessory() {
        return accessory;
    }

    public Body getBody() {
        return body;
    }

    public Engine getEngine() {
        return engine;
    }

    @Override
    public String toString() {
        return "Auto " + id +
                " (Body: " + body.getId() +
                ", Motor: " + engine.getId() +
                ", Accessory: " + accessory.getId() +
                ')';
    }
}
