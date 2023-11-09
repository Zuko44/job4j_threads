package ru.job4j;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

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
}