package ua.unsober.backend.feature.caching;

import lombok.SneakyThrows;
import org.springframework.cache.Cache;
import org.springframework.lang.NonNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class LRUCache implements Cache {
    private final String name;
    private final Map<Object, Object> cache;

    public LRUCache(final String name, final int maxSize) {
        this.name = name;
        this.cache = new LinkedHashMap<>(32, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(final Map.Entry<Object, Object> eldest) {
                return size() > maxSize;
            }
        };
    }

    @Override
    public @NonNull String getName() {
        return name;
    }

    @Override
    public @NonNull Object getNativeCache() {
        return cache;
    }

    @Override
    public ValueWrapper get(@NonNull Object key) {
        Object value = cache.get(key);
        return value != null ? () -> value : null;
    }

    @Override
    public <T> T get(@NonNull Object key, Class<T> type) {
        Object value = cache.get(key);
        if (value == null)
            return null;
        return type.cast(value);
    }

    @SneakyThrows
    @Override
    public <T> T get(@NonNull Object key, @NonNull Callable<T> valueLoader) {
        @SuppressWarnings("unchecked")
        T value = (T) cache.get(key);
        if (value != null)
            return value;
        T loaded = valueLoader.call();
        cache.put(key, loaded);
        return loaded;
    }

    @Override
    public void put(@NonNull Object key, Object value) {
        cache.put(key, value);
    }

    @Override
    public void evict(@NonNull Object key) {
        cache.remove(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }
}