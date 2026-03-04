package com.logsense.platform.common.logging.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LoggingJsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String toCompactJson(String raw) {
        if (raw == null || raw.isBlank()) return raw;
        try {
            Object json = MAPPER.readValue(raw, Object.class);
            return MAPPER.writeValueAsString(json);
        } catch (Exception e) {
            return raw;
        }
    }
}
