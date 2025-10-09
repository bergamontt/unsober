package ua.unsober.backend.feature.building;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@ConditionalOnMissingBean(MapService.class)
public class DefaultMapService implements MapService{
    @Override
    public byte[] getMap(BigDecimal latitude, BigDecimal longitude) {
        return null;
    }
}