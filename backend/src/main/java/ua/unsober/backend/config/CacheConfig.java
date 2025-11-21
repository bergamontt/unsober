package ua.unsober.backend.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.unsober.backend.feature.caching.LRUCacheManager;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public LRUCacheManager cacheManager() {
        return new LRUCacheManager(500);
    }

}