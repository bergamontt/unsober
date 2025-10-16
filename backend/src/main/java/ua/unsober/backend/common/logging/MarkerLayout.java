package ua.unsober.backend.common.logging;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;
import lombok.Setter;
import org.slf4j.Marker;

import java.util.stream.Collectors;

public class MarkerLayout extends LayoutBase<ILoggingEvent> {

    @Setter
    private String pattern;

    private final PatternLayout patternLayout = new PatternLayout();

    @Override
    public void start() {
        patternLayout.setContext(getContext());
        patternLayout.setPattern(pattern);
        patternLayout.start();
        super.start();
    }

    @Override
    public String doLayout(ILoggingEvent event) {
        String markerPart = "";
        if (event.getMarkerList() != null) {
            markerPart = "[" + event.getMarkerList().stream()
                    .map(Marker::getName)
                    .collect(Collectors.joining(", ")) + "] ";
        }

        return patternLayout.doLayout(event).replace(
                event.getFormattedMessage(),
                markerPart + event.getFormattedMessage());
    }
}