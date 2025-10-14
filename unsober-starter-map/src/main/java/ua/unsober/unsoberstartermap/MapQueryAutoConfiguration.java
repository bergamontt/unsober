package ua.unsober.unsoberstartermap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.client.RestTemplate;

@AutoConfiguration
@ConditionalOnClass(RestTemplate.class)
@EnableConfigurationProperties(MapQueryApiKey.class)
@RequiredArgsConstructor
public class MapQueryAutoConfiguration {
    private final MapQueryApiKey properties;
    private final RestTemplateBuilder restTemplateBuilder;

    @Bean
    @ConditionalOnMissingBean(MapQueryProperties.class)
    public MapQueryProperties defaultMapQueryProperties() {
        return MapQueryProperties.builder()
                .width(600)
                .height(400)
                .zoom(16)
                .build();
    }

    @Bean
    @ConditionalOnProperty(name = "geoapify.api-key")
    @ConditionalOnMissingBean(MapService.class)
    public MapService mapServiceWithKey(MapQueryProperties props) {
        String apiKey = properties.getApiKey();
        RestTemplate restTemplate = restTemplateBuilder.build();
        return new MapServiceImpl(apiKey, restTemplate,  props);
    }

    @Bean
    @ConditionalOnMissingBean(MapService.class)
    public MapService defaultMapService() {
        return new DefaultMapService();
    }
}
