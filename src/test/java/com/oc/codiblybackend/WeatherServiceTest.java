package com.oc.codiblybackend;


import com.oc.codiblybackend.client.Forecast;
import com.oc.codiblybackend.client.OpenMeteoClient;
import com.oc.codiblybackend.dto.WeatherResponseDTO;
import com.oc.codiblybackend.service.WeatherService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WeatherServiceTest {
    @Mock
    private OpenMeteoClient openMeteoClient;

    @InjectMocks
    private WeatherService weatherService;

    private Forecast mockForecast() {
        List<LocalDate> dailyDates = List.of(
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2),
                LocalDate.now().plusDays(3),
                LocalDate.now().plusDays(4),
                LocalDate.now().plusDays(5),
                LocalDate.now().plusDays(6)
        );

        List<Integer> weatherCodes = List.of(0, 1, 2, 3, 45, 51, 61);
        List<Float> tempMin = List.of(10f, 11f, 9f, 8f, 12f, 10f, 11f);
        List<Float> tempMax = List.of(20f, 21f, 19f, 18f, 22f, 20f, 21f);
        List<Float> sunshine = List.of(3600f, 7200f, 1800f, 0f, 5400f, 6000f, 3600f);

        Forecast.Daily daily = new Forecast.Daily(
                dailyDates,
                weatherCodes,
                tempMin,
                tempMax,
                sunshine
        );

        Forecast.DailyUnits dailyUnits = new Forecast.DailyUnits(
                "iso8601",
                "wmo code",
                "°C",
                "°C",
                "s"
        );

        List<String> hourlyTimes = new ArrayList<>();
        List<Float> surfacePressure = new ArrayList<>();
        for (int day = 0; day < 7; day++) {
            LocalDate date = LocalDate.now().plusDays(day);
            for (int hour = 0; hour < 24; hour++) {
                String timestamp = String.format("%sT%02d:00", date, hour);
                hourlyTimes.add(timestamp);
                surfacePressure.add(1010f + (hour % 5));  // sample variation
            }
        }

        Forecast.Hourly hourly = new Forecast.Hourly(hourlyTimes, surfacePressure);

        Forecast.HourlyUnits hourlyUnits = new Forecast.HourlyUnits("iso8601", "hPa");

        return new Forecast(
                52.23f,
                21.01f,
                0.0f,
                0,
                "Europe/Warsaw",
                "CEST",
                100,
                daily,
                dailyUnits,
                hourly,
                hourlyUnits
        );
    }



    @Test
    void testGetForecast_ShouldReturn7DayForecast() {
        Forecast mockForecast = mockForecast();

        when(openMeteoClient.getForecast(
                anyFloat(),
                anyFloat(),
                anyList(),
                anyList(),
                eq("auto")
        )).thenReturn(mockForecast);

        List<WeatherResponseDTO> result = weatherService.getForecast(52.23f, 21.01f);

        assertEquals(7, result.size());
    }

}
