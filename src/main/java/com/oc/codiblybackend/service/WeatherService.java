package com.oc.codiblybackend.service;

import com.oc.codiblybackend.client.Forecast;
import com.oc.codiblybackend.client.OpenMeteoClient;
import com.oc.codiblybackend.dto.WeatherResponseDTO;
import com.oc.codiblybackend.dto.WeeklySummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final OpenMeteoClient openMeteoClient;

    private static final double POWER = 2.5;
    private static final double EFFICIENCY = 0.2;
    private static final int MIN_RAINY_DAY_CODE = 51;

    public List<WeatherResponseDTO> getForecast(float lat, float lon){
        List<String> dailyParams = List.of("weathercode", "temperature_2m_min", "temperature_2m_max", "sunshine_duration");
        List<String> hourlyParams = List.of();
        Forecast forecast = openMeteoClient.getForecast(lat, lon, dailyParams, hourlyParams,"auto");
        Forecast.Daily daily = forecast.daily();

        List<WeatherResponseDTO> result = new ArrayList<>();

        for(int i = 0; i < daily.time().size(); i++){
            float sunshineSeconds = daily.sunshineDuration().get(i);
            double estimatedEnergy = calculateEstimatedEnergy(sunshineSeconds);

            WeatherResponseDTO dto = WeatherResponseDTO.builder()
                    .date(daily.time().get(i))
                    .weatherCode(daily.weatherCode().get(i))
                    .temperatureMin(daily.temperatureMin().get(i))
                    .temperatureMax(daily.temperatureMax().get(i))
                    .sunshineDuration(sunshineSeconds)
                    .estimatedEnergy(estimatedEnergy)
                    .build();

            result.add(dto);
        }

        return result;
    }

    public WeeklySummaryDTO getWeeklySummary(float lat, float lon){
        List<String> dailyParams = List.of(
                "temperature_2m_max", "temperature_2m_min", "weathercode", "sunshine_duration"
        );
        List<String> hourlyParams = List.of("surface_pressure");
        String timezone = "auto";

        Forecast forecast = openMeteoClient.getForecast(lat, lon, dailyParams, hourlyParams, timezone);

        List<Float> pressures = forecast.hourly().surfacePressure();
        List<Float> sunshineDurations = forecast.daily().sunshineDuration();
        List<Float> maxTemps = forecast.daily().temperatureMax();
        List<Float> minTemps = forecast.daily().temperatureMin();
        List<Integer> weatherCodes = forecast.daily().weatherCode();

        double avgPressure = pressures.stream().mapToDouble(Float::doubleValue).average().orElse(Double.NaN);
        double avgSunshine = sunshineDurations.stream().mapToDouble(Float::doubleValue).average().orElse(Double.NaN);
        float minTemperature = minTemps.stream().min(Float::compare).orElse(Float.NaN);
        float maxTemperature = maxTemps.stream().max(Float::compare).orElse(Float.NaN);

        long rainyDays = weatherCodes.stream().filter(code -> code >= MIN_RAINY_DAY_CODE).count();

        String summary = (rainyDays >= 4) ? "rainy week" : "mostly dry";

        return WeeklySummaryDTO.builder()
                .averagePressure(avgPressure)
                .averageSunshineDurationSeconds(avgSunshine)
                .minTemperature(minTemperature)
                .maxTemperature(maxTemperature)
                .weatherSummary(summary)
                .build();
    }

    private double calculateEstimatedEnergy(double sunshineSeconds){
        double sunshineHours = sunshineSeconds / 3600;
        return EFFICIENCY * POWER * sunshineHours;
    }
}

