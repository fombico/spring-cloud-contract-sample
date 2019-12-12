package com.fombico.producer;

import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class AppDateTime {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    public String getCurrentYearToMillisecondDateTime() {
        return DATE_FORMAT.format(ZonedDateTime.now(ZoneId.of("UTC")));
    }
}
