package com.oc.codiblybackend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class WeatherForecastControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testMissingLatLonParams_ShouldReturn400() throws Exception {
        mockMvc.perform(get("/api/forecast"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testWeeklySummary_ValidInput_ShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/forecast/summary")
                        .param("latitude", "52.23")
                        .param("longitude", "21.01"))
                .andExpect(status().isOk());
    }

}

