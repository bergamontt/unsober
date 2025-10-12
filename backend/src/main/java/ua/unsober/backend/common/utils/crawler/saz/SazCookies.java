package ua.unsober.backend.common.utils.crawler.saz;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Setter
@Getter
public class SazCookies {
    private Map<String, String> cookies;
}