package com.example.mediateltesttask;


import com.example.mediateltesttask.to.ForecastApiResponse;
import com.example.mediateltesttask.util.ForecastClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.example.mediateltesttask.TestData.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
public class ForecastClientTest {

    private ForecastClient forecastClient;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        forecastClient = new ForecastClient(restTemplate, "http://localhost:8086", "someAppId");
    }

    @Test
    public void shouldCallForecastService() {
        given(restTemplate.getForObject("http://localhost:8086/data/2.5/forecast?lat=55.7522&lon=37.6156&appid=someAppId", ForecastApiResponse.class)).willReturn(FORECAST_API_RESPONSE);

        Optional<ForecastApiResponse> actualResponse = forecastClient.fetchForecasts(MOSCOW_LON, MOSCOW_LAT);

        assertThat(actualResponse, is(Optional.of(FORECAST_API_RESPONSE)));
    }

    @Test
    public void shouldReturnEmptyOptionalIfWeatherServiceIsUnavailable() {
        given(restTemplate.getForObject("http://localhost:8086/data/2.5/forecast?lat=55.7522&lon=37.6156&appid=someAppId", ForecastApiResponse.class)).willThrow(new RestClientException("Неверные координаты"));

        Optional<ForecastApiResponse> actualResponse = forecastClient.fetchForecasts(MOSCOW_LON, MOSCOW_LAT);

        assertThat(actualResponse, is(Optional.empty()));
    }
}
