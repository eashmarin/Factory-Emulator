package ru.nsu.ccfit.factory.product;

public abstract sealed class Product permits Accessory, Body, Engine, Auto {
    protected final int id;

    public Product(int id) {
        this.id = id;
    }

    public final int getId() {
        return id;
    }
}
