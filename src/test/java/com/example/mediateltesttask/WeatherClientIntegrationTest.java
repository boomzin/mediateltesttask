package com.example.mediateltesttask;

import com.example.mediateltesttask.helper.FileLoader;
import com.example.mediateltesttask.to.WeatherApiResponse;
import com.example.mediateltesttask.util.WeatherClient;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import static com.example.mediateltesttask.TestData.WEATHER_API_RESPONSE;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@SpringBootTest
@WireMockTest(httpPort = 8086)
public class WeatherClientIntegrationTest {

    @Autowired
    private WeatherClient weatherClient;

    @Test
    public void shouldCallWeatherService() {
        stubFor(get(urlEqualTo("/data/2.5/weather?q=moscow,ru&appid=someAppId")).willReturn(aResponse().withBody(FileLoader.read("classpath:weatherApiResponse.json")).withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).withStatus(200)));

        WeatherApiResponse actualWeatherResponse = weatherClient.fetchCurrentWeather("moscow", "ru").get();
        Assertions.assertEquals(WEATHER_API_RESPONSE.getCoord().getLon(), actualWeatherResponse.getCoord().getLon());
        Assertions.assertEquals(WEATHER_API_RESPONSE.getCoord().getLat(), actualWeatherResponse.getCoord().getLat());
        Assertions.assertEquals(WEATHER_API_RESPONSE.getMain().getTemp(), actualWeatherResponse.getMain().getTemp());
    }
}
