package com.oc.codiblybackend.client;

import org.springframework.cloud.openfeign.CollectionFormat;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "OpenMeteo",
        url = "https://api.open-meteo.com/v1"
)

public interface OpenMeteoClient {

    @GetMapping("/forecast")
    @CollectionFormat(feign.CollectionFormat.CSV)
    Forecast getForecast(
            @RequestParam float latitude,
            @RequestParam float longitude,
            @RequestParam(required = false) List<String> daily,
            @RequestParam(required = false) List<String> hourly,
            @RequestParam String timezone
    );
}
