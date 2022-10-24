package com.example.mediateltesttask;

import com.example.mediateltesttask.exception.AppException;
import com.example.mediateltesttask.model.CityForecast;
import com.example.mediateltesttask.repository.CityForecastRepository;
import com.example.mediateltesttask.service.CityForecastService;
import com.example.mediateltesttask.to.WeatherApiResponse;
import com.example.mediateltesttask.util.ForecastClient;
import com.example.mediateltesttask.util.WeatherClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static com.example.mediateltesttask.TestData.*;

@ExtendWith(MockitoExtension.class)
public class CityForecastServiceTest {

    @Mock
    private ForecastClient forecastClient;

    @Mock
    private WeatherClient weatherClient;

    @Mock
    private CityForecastRepository repository;

    @InjectMocks
    private CityForecastService service;

    @Test()
    public void shouldThrowExceptionWrongCity() {
        Mockito.when(weatherClient.fetchCurrentWeather("WRONG", "DATA")).thenReturn(Optional.empty());
        AppException thrown = assertThrows(AppException.class, () -> service.getCurrentTemperature("WRONG", "DATA"));
    }

    @Test
    public void shouldGetWeatherResponse() throws AppException {
        Mockito.when(weatherClient.fetchCurrentWeather("moscow", "ru")).thenReturn(Optional.of(WEATHER_API_RESPONSE));
        WeatherApiResponse response = service.getCurrentTemperature("moscow", "ru");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(WEATHER_API_RESPONSE, response);
    }

    @Test
    public void shouldGetCityResponseWithFullBd() throws AppException {
        Mockito.when(repository.getForecastsByThreeDays(MOSCOW_LON, MOSCOW_LAT, START_AT_TOMORROW, END_AT_THIRD_DAY)).thenReturn(FULL_BD);
        CityForecast forecastWithMinTemp = service.checkInDbOrLoadNewForecast(MOSCOW_LON, MOSCOW_LAT);
        Assertions.assertNotNull(forecastWithMinTemp);
        Assertions.assertEquals(SECOND_DAY_CITY_FORECAST, forecastWithMinTemp);
    }

    @Test
    public void shouldGetCityResponseWithHalfBd() throws AppException {
        Mockito.when(repository.getForecastsByThreeDays(MOSCOW_LON, MOSCOW_LAT, START_AT_TOMORROW, END_AT_THIRD_DAY)).thenReturn(HALF_BD);
        Mockito.when(forecastClient.fetchForecasts(MOSCOW_LON, MOSCOW_LAT)).thenReturn(Optional.of(FORECAST_API_RESPONSE));
        CityForecast forecastWithMinTemp = service.checkInDbOrLoadNewForecast(MOSCOW_LON, MOSCOW_LAT);
        Assertions.assertNotNull(forecastWithMinTemp);
        Assertions.assertEquals(FORECAST_WITH_MIN_TEMP_FROM_API, forecastWithMinTemp);
    }

    @Test
    public void shouldGetCityResponseWithEmptyBd() throws AppException {
        Mockito.when(repository.getForecastsByThreeDays(MOSCOW_LON, MOSCOW_LAT, START_AT_TOMORROW, END_AT_THIRD_DAY)).thenReturn(EMPTY_BD);
        Mockito.when(forecastClient.fetchForecasts(MOSCOW_LON, MOSCOW_LAT)).thenReturn(Optional.of(FORECAST_API_RESPONSE));
        CityForecast forecastWithMinTemp = service.checkInDbOrLoadNewForecast(MOSCOW_LON, MOSCOW_LAT);
        Assertions.assertNotNull(forecastWithMinTemp);
        Assertions.assertEquals(FORECAST_WITH_MIN_TEMP_FROM_API, forecastWithMinTemp);
    }
}
