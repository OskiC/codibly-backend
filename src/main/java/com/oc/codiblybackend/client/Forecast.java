package com.oc.codiblybackend.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Forecast (
        float latitude,
        float longitude,
        @JsonProperty("generationtime_ms")
        float generationTimeMs,
        @JsonProperty("utc_offset_seconds")
        int utcOffsetSeconds,
        String timezone,
        @JsonProperty("timezone_abbreviation")
        String timezoneAbbreviation,
        int elevation,
        @JsonProperty("daily")
        Daily daily,
        @JsonProperty("daily_units")
        DailyUnits dailyUnits,
        @JsonProperty("hourly")
        Hourly hourly,
        @JsonProperty("hourly_units")
        HourlyUnits hourlyUnits
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Daily(
            List<LocalDate> time,
            @JsonProperty("weathercode")
            List<Integer> weatherCode,
            @JsonProperty("temperature_2m_min")
            List<Float> temperatureMin,
            @JsonProperty("temperature_2m_max")
            List<Float> temperatureMax,
            @JsonProperty("sunshine_duration")
            List<Float> sunshineDuration
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record DailyUnits(
            String time,
            @JsonProperty("weathercode")
            String weatherCode,
            @JsonProperty("temperature_2m_min")
            String temperatureMin,
            @JsonProperty("temperature_2m_max")
            String temperatureMax,
            @JsonProperty("sunshine_duration")
            String sunshineDuration
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Hourly(
            List<String> time,
            @JsonProperty("surface_pressure")
            List<Float> surfacePressure
    ) {}

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record HourlyUnits(
            String time,
            @JsonProperty("surface_pressure")
            String surfacePressure
    ) {}
}
