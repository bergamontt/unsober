package ua.unsober.unsoberstartermap;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfigureAfter(MapQueryPrimaryConfiguration.class)
public class MapQueryFallbackConfiguration {
    @Bean
    @ConditionalOnMissingBean(MapService.class)
    public MapService defaultMapService() {
        return new DefaultMapService();
    }
}
