package com.oc.codiblybackend.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record WeatherResponseDTO (
    LocalDate date,
    int weatherCode,
    float temperatureMin,
    float temperatureMax,
    float sunshineDuration,
    double estimatedEnergy
){}

