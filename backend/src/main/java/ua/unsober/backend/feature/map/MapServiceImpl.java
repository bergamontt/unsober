package ua.unsober.backend.feature.map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.util.Locale;

@Service
public class MapServiceImpl implements MapService {
    private final static String formatUrl = "https://maps.geoapify.com/v1/staticmap?" +
            "style=osm-carto" +
            "&width=600&height=400" +
            "&center=lonlat:%f,%f" +
            "&zoom=16" +
            "&marker=lonlat:%f,%f" +
            ";type:material;color:red;size:large" +
            "&apiKey=%s";

    @Value("${geoapify.api-key}")
    private String apiKey;

    private final RestClient restClient = RestClient.create();

    @Override
    public byte[] getMap(BigDecimal latitude, BigDecimal longitude) {
        double lon = longitude.doubleValue();
        double lat = latitude.doubleValue();
        String url = String.format(Locale.US, formatUrl, lon, lat, lon, lat, apiKey);
        return restClient.get().uri(url).retrieve().body(byte[].class);
    }
}
