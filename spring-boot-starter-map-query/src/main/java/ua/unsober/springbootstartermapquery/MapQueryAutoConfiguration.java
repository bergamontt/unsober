package ua.unsober.springbootstartermapquery;

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
@EnableConfigurationProperties(MapQueryProperties.class)
@RequiredArgsConstructor
public class MapQueryAutoConfiguration {
    private final MapQueryProperties properties;
    private final RestTemplateBuilder restTemplateBuilder;

    @Bean
    @ConditionalOnProperty(name = "geoapify.api-key")
    @ConditionalOnMissingBean(MapService.class)
    public MapService mapServiceWithKey() {
        String apiKey = properties.getApiKey();
        RestTemplate restTemplate = restTemplateBuilder.build();
        return new MapServiceImpl(apiKey, restTemplate);
    }

    @Bean
    @ConditionalOnMissingBean(MapService.class)
    public MapService defaultMapService() {
        return new DefaultMapService();
    }
}
