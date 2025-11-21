package ua.unsober.backend.feature.caching;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
@RequiredArgsConstructor
public class CacheController {
    private final LRUCacheManager cacheManager;

    @DeleteMapping("/clear/{name}")
    public void clear(@PathVariable String name) {
        cacheManager.clear(name);
    }
}