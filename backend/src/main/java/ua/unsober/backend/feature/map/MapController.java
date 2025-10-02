package ua.unsober.backend.feature.map;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/map")
@RequiredArgsConstructor
public class MapController {
    private final MapService mapService;

    @GetMapping(produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getMap(
            @RequestParam BigDecimal lat,
            @RequestParam BigDecimal lon) {
        return mapService.getMap(lat, lon);
    }
}
