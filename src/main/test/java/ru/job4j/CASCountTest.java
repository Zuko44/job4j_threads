package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {
    @Test
    void whenIncrementThan2() {
        CASCount cas = new CASCount();
        cas.increment();
        cas.increment();
        assertThat(cas.get()).isEqualTo(2);
    }

    @Test
    void whenIncrementThen0() {
        CASCount cas = new CASCount();
        assertThat(cas.get()).isEqualTo(0);
    }

    @Test
    void whenIncrementThen5() {
        CASCount cas = new CASCount();
        cas.increment();
        cas.increment();
        cas.increment();
        cas.increment();
        cas.increment();
        assertThat(cas.get()).isEqualTo(5);
    }

    @Test
    void when3ThreadsThanIncrementThan9() throws InterruptedException {
        CASCount cas = new CASCount();
        Thread first = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                cas.increment();
            }
        });
        Thread second = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                cas.increment();
            }
        });
        Thread third = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                cas.increment();
            }
        });
        first.start();
        second.start();
        third.start();
        first.join();
        second.join();
        third.join();
        assertThat(cas.get()).isEqualTo(9);
    }
}