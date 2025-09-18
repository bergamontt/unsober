package ua.unsober.backend.service;

import java.math.BigDecimal;

public interface MapService {
    byte[] getMap(BigDecimal latitude, BigDecimal longitude);
}
