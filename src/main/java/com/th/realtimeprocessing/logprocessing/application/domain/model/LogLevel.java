package com.th.realtimeprocessing.logprocessing.application.domain.model;

public enum LogLevel {
    ERROR,
    TRACE,
    DEBUG,
    INFO,
    WARN;

    public static LogLevel parseLogLevel(String logLevel) {
        try {
            return LogLevel.valueOf(logLevel);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
