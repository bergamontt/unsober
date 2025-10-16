package ua.unsober.backend.common.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;
import lombok.Setter;
import org.slf4j.Marker;

import java.util.stream.Collectors;

public class ColorLayout extends LayoutBase<ILoggingEvent> {

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";

    @Setter
    private String pattern;

    private PatternLayout patternLayout;

    @Override
    public void start() {
        patternLayout = new PatternLayout();
        patternLayout.setContext(getContext());
        patternLayout.setPattern(pattern);
        patternLayout.start();
        super.start();
    }

    @Override
    public String doLayout(ILoggingEvent event) {
        String color = switch (event.getLevel().toInt()) {
            case Level.ERROR_INT -> RED;
            case Level.WARN_INT -> YELLOW;
            case Level.INFO_INT -> GREEN;
            case Level.DEBUG_INT -> BLUE;
            case Level.TRACE_INT -> CYAN;
            default -> RESET;
        };

        String line = patternLayout.doLayout(event);
        String level = event.getLevel().toString();
        String colorized = color + level + RESET;
        line = line.replace(level, colorized);

        String markerPart = "[" + event.getMarkerList().stream()
                .map(Marker::getName)
                .collect(Collectors.joining(", ")) + "] ";

        return line.replace(event.getFormattedMessage(), markerPart + event.getFormattedMessage());
    }
}