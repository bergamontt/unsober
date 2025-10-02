package ua.unsober.backend.common.utils.crawler.saz;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "saz-crawler")
public class SazCookies {
    private Map<String, String> cookies;
}