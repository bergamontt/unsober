package ua.unsober.unsoberstartermap;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MapQueryProperties {
    private int width;
    private int height;
    private int zoom;
}