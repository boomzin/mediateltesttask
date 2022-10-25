package com.example.mediateltesttask;


import com.example.mediateltesttask.to.WeatherApiResponse;
import com.example.mediateltesttask.util.WeatherClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static com.example.mediateltesttask.TestData.*;

@ExtendWith(SpringExtension.class)
public class WeatherClientTest {

    private WeatherClient weatherClient;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() {
        weatherClient = new WeatherClient(restTemplate, "http://localhost:8086", "someAppId");
    }

    @Test
    public void shouldCallWeatherService() {
        given(restTemplate.getForObject("http://localhost:8086/data/2.5/weather?q=moscow,ru&appid=someAppId", WeatherApiResponse.class)).willReturn(WEATHER_API_RESPONSE);

        Optional<WeatherApiResponse> actualResponse = weatherClient.fetchCurrentWeather("moscow", "ru");

        assertThat(actualResponse, is(Optional.of(WEATHER_API_RESPONSE)));
    }

    @Test
    public void shouldReturnEmptyOptionalIfWeatherServiceIsUnavailable() {
        given(restTemplate.getForObject("http://localhost:8089/data/2.5/weather?q=moscow,ru&appid=someAppId", WeatherApiResponse.class)).willThrow(new RestClientException("Нет такого города"));

        Optional<WeatherApiResponse> actualResponse = weatherClient.fetchCurrentWeather("moscow", "ru");

        assertThat(actualResponse, is(Optional.empty()));
    }
}
