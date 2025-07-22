package com.th.realtimeprocessing.logprocessing.application.domain.model;

import java.util.UUID;

public record LogId (
        String uuid
)
{
    public static LogId generate() {
        return new LogId(
                UUID.randomUUID().toString()
        );
    }
}

