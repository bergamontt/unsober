package ua.unsober.backend.common.logging;

import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomFileAppender extends AppenderBase<ILoggingEvent> {

    private PrintWriter writer;
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    private static final SimpleDateFormat smallFormatter = new SimpleDateFormat("HH:mm:ss");

    @Override
    public void start() {
        File folder = new File("logs");
        if (!folder.exists()) folder.mkdir();

        String fileName = "logs/" + formatter.format(new Date(System.currentTimeMillis())) + ".log";
        try {
            writer = new PrintWriter(new FileWriter(fileName, true), true);
            super.start();
        } catch (IOException e) {
            addError("Failed to open file: " + fileName, e);
        }
    }

    @Override
    protected void append(ILoggingEvent event) {
        if (writer != null) {
            writer.println(
                    smallFormatter.format(new Date(event.getTimeStamp())) +
                    " [" + event.getLevel() + "] " +
                    event.getThreadName() + " " +
                    event.getLoggerName() + " - " +
                    event.getFormattedMessage()
            );
        }
    }

    @Override
    public void stop() {
        if (writer != null) {
            writer.close();
        }
        super.stop();
    }
}
