package com.oc.codiblybackend.dto;

import lombok.Builder;

@Builder
public record WeeklySummaryDTO (
    double averagePressure,
    double averageSunshineDurationSeconds,
    float minTemperature,
    float maxTemperature,
    String weatherSummary
){}

