package ua.unsober.unsoberstartermap;

import java.math.BigDecimal;

public interface MapService {
    byte[] getMap(BigDecimal latitude, BigDecimal longitude);
}
