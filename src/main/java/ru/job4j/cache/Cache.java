package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (key, value) -> {
            if (model.getVersion() != value.getVersion()) {
                throw new OptimisticException("wrong version");
            }
            Base updated = new Base(value.getId(), value.getVersion() + 1);
            updated.setName(model.getName());
            return updated;
        }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }
}
