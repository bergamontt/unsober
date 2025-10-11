package ua.unsober.springbootstartermapquery;

import lombok.RequiredArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Locale;

@RequiredArgsConstructor
public class MapServiceImpl implements MapService {
    private static final String formatUrl = "https://maps.geoapify.com/v1/staticmap?" +
            "style=osm-carto" +
            "&width=600&height=400" +
            "&center=lonlat:%f,%f" +
            "&zoom=16" +
            "&marker=lonlat:%f,%f" +
            ";type:material;color:red;size:large" +
            "&apiKey=%s";

    private final String apiKey;
    private final RestTemplate restTemplate;

    @Override
    public byte[] getMap(BigDecimal latitude, BigDecimal longitude) {
        if (latitude == null || longitude == null) return null;
        double lon = longitude.doubleValue();
        double lat = latitude.doubleValue();
        String url = String.format(Locale.US, formatUrl, lon, lat, lon, lat, apiKey);
        return restTemplate.getForObject(url, byte[].class);
    }
}
