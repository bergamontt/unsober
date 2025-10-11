package ua.unsober.unsoberstartermap;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "geoapify")
public class MapQueryProperties {
    private String apiKey = "";
}
