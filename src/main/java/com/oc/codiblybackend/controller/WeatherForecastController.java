package com.oc.codiblybackend.controller;


import com.oc.codiblybackend.dto.WeatherResponseDTO;
import com.oc.codiblybackend.dto.WeeklySummaryDTO;
import com.oc.codiblybackend.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class WeatherForecastController {
    private final WeatherService weatherService;

    @GetMapping("/forecast")
    public ResponseEntity<List<WeatherResponseDTO>> get7DayForecast(
            @Validated
            @ParameterObject
            WeatherValidate req
    ){
        List<WeatherResponseDTO> forecast = weatherService.getForecast(req.latitude(), req.longitude());
        return ResponseEntity.ok(forecast);
    }

    @GetMapping("/forecast/summary")
    public ResponseEntity<WeeklySummaryDTO> getWeeklySummary(
            @Validated
            @ParameterObject
            WeatherValidate req
    ) {
        var summary = weatherService.getWeeklySummary(req.latitude(), req.longitude());
        return ResponseEntity.ok(summary);
    }
}
