package ru.nsu.ccfit.factory.storage;

import ru.nsu.ccfit.factory.product.Product;

import java.util.ArrayDeque;
import java.util.Queue;

public class Storage<T extends Product> {

    private final int limit;
    private final Queue<T> queue;

    public Storage(int limit) {
        this.limit = limit;
        queue = new ArrayDeque<>(limit);
    }

    public int getLimit() {
        return limit;
    }

    public int getSize() {
        return queue.size();
    }

    public synchronized void addProduct(T product) throws InterruptedException {
        while (getLimit() == getSize()) {
            wait();
        }
        queue.add(product);
        notify();
    }

    public synchronized T pollProduct() throws InterruptedException {
        T product;
        while ((product = queue.poll()) == null) {
            wait();
        }
        notify();
        return product;
    }
}
