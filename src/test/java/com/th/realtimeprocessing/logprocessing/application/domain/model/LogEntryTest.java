package com.th.realtimeprocessing.logprocessing.application.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LogEntryTest {

    @Test
    @DisplayName("Should create Log Entry with valid data")
    void shouldCreateLogEntryFromCommand() {
        // Arrange
        Instant timestamp = Instant.now();

        // Act
        Optional<LogEntry> logEntry = LogEntry.of(
                "1.1.1.1",
                timestamp,
                "ERROR",
                "Test Message"
                );

        // Assert
        assertAll(
                () -> assertTrue(logEntry.isPresent()),
                () -> assertEquals(LogLevel.ERROR, logEntry.get().getLogLevel()),
                () -> assertEquals("1.1.1.1", logEntry.get().getSourceServer()),
                () -> assertEquals("Test Message", logEntry.get().getMessage()),
                () -> assertEquals(timestamp, logEntry.get().getTimestamp()),
                () -> assertNotEquals(null, logEntry.get().getLogId())
        );
    }

    @Test
    @DisplayName("Should reject log entry with future timestamp")
    void shouldRejectFutureTimestamp() {
        // Arrange
        Instant futureTimestamp = Instant.now().plusSeconds(10);

        // Act
        Optional<LogEntry> logEntry = LogEntry.of(
                "1.1.1.1",
                futureTimestamp,
                "ERROR",
                "Test Message"
        );

        // Assert
        assertFalse(logEntry.isPresent());
    }

    @Test
    @DisplayName("Should reject invalid log level")
    void shouldRejectInvalidLogLevel() {
        // Arrange
        String invalidLogLevel = "error";

        // Act
        Optional<LogEntry> logEntry = LogEntry.of(
                "1.1.1.1",
                Instant.now(),
                invalidLogLevel,
                "Test Message"
        );

        // Assert
        assertFalse(logEntry.isPresent());
    }

    @Test
    @DisplayName("Should handle boundary timestamp (exactly 5 seconds in future)")
    void shouldHandleBoundaryTimestamp() {
        // Arrange & Act
        Optional<LogEntry> logEntry = LogEntry.of(
                "1.1.1.1",
                Instant.now().plusSeconds(5),
                "INFO",
                "Test Message"
        );

        // Assert
        assertTrue(logEntry.isPresent());
    }

}