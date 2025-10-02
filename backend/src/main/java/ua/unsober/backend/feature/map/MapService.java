package ua.unsober.backend.feature.map;

import java.math.BigDecimal;

public interface MapService {
    byte[] getMap(BigDecimal latitude, BigDecimal longitude);
}
