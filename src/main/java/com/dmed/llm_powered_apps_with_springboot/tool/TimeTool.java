package com.dmed.llm_powered_apps_with_springboot.tool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.ZoneId;

@Component
@Slf4j
public class TimeTool {

    @Tool(name = "getCurrentLocalTime", description = "Returns ONLY the system's local time. Use this when the user explicitly asks for local/system time.")
    public String getCurrentLocalTime() {
        LocalTime time = LocalTime.now();
        log.info("getCurrentLocalTime called, returning {}", time);
        return time.toString();
    }

    @Tool(name = "getCurrentTimeForZone", description = "Returns the time for a specific timezone. Use ONLY when the user explicitly asks for a timezone.")
    public String getCurrentTimeForZone(
            @ToolParam(description = "IANA timezone, e.g., 'Europe/London' or 'America/Chicago'") String timeZone
    ) {
        try {
            LocalTime time = LocalTime.now(ZoneId.of(timeZone));
            log.info("getCurrentTimeForZone called with {}, returning {}", timeZone, time);
            return time.toString();
        } catch (DateTimeException e) {
            log.error("Invalid timezone: {}", timeZone);
            return "Invalid timezone: " + timeZone;
        }
    }
}