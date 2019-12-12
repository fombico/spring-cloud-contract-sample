package com.fombico.producer;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class TestUtils {
    private static final DateTimeFormatter YEAR_TO_MILLISECOND_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    static String getCurrentDateTime() {
        return YEAR_TO_MILLISECOND_DATE_FORMAT.format(ZonedDateTime.now(ZoneId.of("UTC")));
    }
}
