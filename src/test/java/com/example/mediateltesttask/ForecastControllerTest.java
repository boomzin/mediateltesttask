package com.example.mediateltesttask;

import com.example.mediateltesttask.exception.AppException;
import com.example.mediateltesttask.service.CityForecastService;
import com.example.mediateltesttask.to.TemperatureResponse;
import com.example.mediateltesttask.web.ForecastController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.mediateltesttask.TestData.*;

@ExtendWith(MockitoExtension.class)
public class ForecastControllerTest {

    @InjectMocks
    private ForecastController controller;

    @Mock
    private CityForecastService service;

    @Test
    public void ShouldReturnResult() throws AppException {
        Mockito.when(service.getCurrentTemperature("moscow", "ru")).thenReturn(WEATHER_API_RESPONSE);
        Mockito.when(service.checkInDbOrLoadNewForecast(MOSCOW_LON, MOSCOW_LAT)).thenReturn(FORECAST_WITH_MIN_TEMP_FROM_API);
        TemperatureResponse expected = new TemperatureResponse(WEATHER_API_RESPONSE.getMain().getTemp(),
                FORECAST_WITH_MIN_TEMP_FROM_API.getTemperature());
        TemperatureResponse actual= controller.getTemp("moscow", "ru");
        Assertions.assertEquals(expected, actual);
    }
}
