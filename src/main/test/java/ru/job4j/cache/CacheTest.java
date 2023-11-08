package ru.job4j.cache;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CacheTest {
    @Test
    void whenAddModel() {
        Cache cache = new Cache();
        Base expected = new Base(1, 0);
        assertThat(cache.add(expected)).isNotNull();
    }

    @Test
    void whenUpdateModel() {
        Cache cache = new Cache();
        cache.add(new Base(1, 0));
        assertThat(cache.update(new Base(2, 0))).isNotNull();
    }

    @Test
    void whenAddModelThanAddThanFalse() {
        Cache cache = new Cache();
        Base model2 = new Base(2, 0);
        cache.add(model2);
        assertThat(cache.add(model2)).isFalse();
    }

    @Test
    void whenAddModelThanDeleteThanTrue() {
        Cache cache = new Cache();
        Base model = new Base(1, 0);
        cache.add(model);
        cache.delete(model);
        assertThat(cache.add(model)).isTrue();
    }
}