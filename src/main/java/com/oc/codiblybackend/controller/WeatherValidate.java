package com.oc.codiblybackend.controller;


import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record WeatherValidate (
        @NotNull(message = "Value cannot be null")
        @Range(min = -90, max = 90, message = "Latitude must be between -90 and 90 deg")
        Float latitude,
        @NotNull(message = "Value cannot be null")
        @Range(min = -90, max = 90, message = "Longitude must be between -90 and 90 deg")
        Float longitude
){}
