package ua.unsober.backend.feature.caching;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ua.unsober.backend.common.BaseSystemTest;

import java.util.Objects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

class CacheSystemTest extends BaseSystemTest {

    @Autowired
    private LRUCacheManager cacheManager;

    private static final String CACHE_NAME = "testCache";

    @AfterEach
    void tearDown() {
        cacheManager.clear(CACHE_NAME);
    }

    @Test
    void clearAsAdminShouldRemoveCache() throws Exception {
        Objects.requireNonNull(cacheManager.getCache(CACHE_NAME)).put("key", "value");
        mvc.perform(delete("/cache/clear/{name}", CACHE_NAME)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
        assertFalse(cacheManager.getCacheNames().contains(CACHE_NAME));
    }

    @Test
    void clearAsStudentShouldBeForbidden() throws Exception {
        Objects.requireNonNull(cacheManager.getCache(CACHE_NAME)).put("key", "value");
        mvc.perform(delete("/cache/clear/{name}", CACHE_NAME)
                        .header("Authorization", "Bearer " + studentToken))
                .andExpect(status().isForbidden());
        assertTrue(cacheManager.getCacheNames().contains(CACHE_NAME));
    }

    @Test
    void clearNonExistentCacheAsAdminShouldNotFail() throws Exception {
        assertFalse(cacheManager.getCacheNames().contains("nonExistentCache"));
        mvc.perform(delete("/cache/clear/{name}", "nonExistentCache")
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
        assertFalse(cacheManager.getCacheNames().contains("nonExistentCache"));
    }

}
