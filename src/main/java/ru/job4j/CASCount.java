package ru.job4j;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class CASCount {
    private final AtomicInteger count = new AtomicInteger();

    public void increment() {
        int ref;
        do {
            ref = count.get();
        } while (!count.compareAndSet(ref, ref + 1));
    }

    public int get() {
        return count.get();
    }
}
