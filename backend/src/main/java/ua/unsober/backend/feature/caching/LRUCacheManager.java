package ua.unsober.backend.feature.caching;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LRUCacheManager implements CacheManager {
    private final Map<String, Cache> caches = new ConcurrentHashMap<>();
    private final int maxCacheSize;

    public LRUCacheManager(int maxCacheSize) {
        this.maxCacheSize = maxCacheSize;
    }

    @Override
    public Cache getCache(@NonNull String name) {
        return caches.computeIfAbsent(name, n -> new LRUCache(n, maxCacheSize));
    }

    @Override
    public @NonNull Collection<String> getCacheNames() {
        return caches.keySet();
    }

    public void clear(String name) {
        caches.remove(name);
    }
}
