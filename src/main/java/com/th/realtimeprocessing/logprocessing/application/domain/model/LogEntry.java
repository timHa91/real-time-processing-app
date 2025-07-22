package com.th.realtimeprocessing.logprocessing.application.domain.model;


import java.time.Instant;
import java.util.Optional;

import static com.th.realtimeprocessing.logprocessing.application.domain.model.LogLevel.parseLogLevel;

public class LogEntry {
    private final LogId logId;
    private final String sourceServer;
    private final Instant timestamp;
    private final LogLevel logLevel;
    private final String message;

    private LogEntry(
            LogId logId,
            String sourceServer,
            Instant timestamp,
            LogLevel logLevel,
            String message
    ) {
        this.logId = logId;
        this.sourceServer = sourceServer;
        this.timestamp = timestamp;
        this.logLevel = logLevel;
        this.message = message;
    }

    public static Optional<LogEntry> of(
            String sourceServer,
            Instant timestamp,
            String logLevel,
            String message
    ) {
        if (timestamp.isAfter(Instant.now().plusSeconds(5))) {
            return Optional.empty(); // → Dead Letter Topic
        }

        LogLevel level = parseLogLevel(logLevel);
        if (level == null) {
            return Optional.empty(); // → Dead Letter Topic
        }

        return Optional.of(
                new LogEntry(
                LogId.generate(),
                sourceServer,
                timestamp,
                level,
                message
        ));
    }

    // === Getter ===

    public LogId getLogId() {
        return logId;
    }

    public String getSourceServer() {
        return sourceServer;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public String getMessage() {
        return message;
    }
}

