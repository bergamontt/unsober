package ua.unsober.backend.common.logging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ColorLayout extends LayoutBase<ILoggingEvent> {

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String CYAN = "\u001B[36m";
    private static final String GRAY = "\u001B[38;5;240m";

    private static final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    @Override
    public String doLayout(ILoggingEvent event) {
        String color;
        String msgColor;

        if (event.getLevel() == Level.ERROR) {
            color = RED;
        } else if (event.getLevel() == Level.WARN) {
            color = YELLOW;
        } else if (event.getLevel() == Level.INFO) {
            color = GREEN;
        } else if (event.getLevel() == Level.DEBUG) {
            color = BLUE;
        } else if (event.getLevel() == Level.TRACE) {
            color = CYAN;
        } else {
            color = RESET;
        }

        if (event.getLevel() != Level.INFO) {
            msgColor = color;
        } else {
            msgColor = RESET;
        }

        return String.format(
                "%s %-14s [%-30s] %-100s - %s%n",
                formatter.format(new Date(event.getTimeStamp())),
                color + event.getLevel() + RESET,
                event.getThreadName(),
                GRAY + event.getLoggerName() + RESET,
                msgColor + event.getFormattedMessage() + RESET
        );
    }
}