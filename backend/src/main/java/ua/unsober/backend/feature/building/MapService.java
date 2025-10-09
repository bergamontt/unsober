package ua.unsober.backend.feature.building;

import java.math.BigDecimal;

public interface MapService {
    byte[] getMap(BigDecimal latitude, BigDecimal longitude);
}
