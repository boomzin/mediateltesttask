package com.example.mediateltesttask.util;

import com.example.mediateltesttask.to.WeatherApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class WeatherClient {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(WeatherClient.class);
    private final RestTemplate restTemplate;
    private final String weatherServiceUrl;
    private final String weatherServiceApiKey;

    @Autowired
    public WeatherClient(final RestTemplate restTemplate, @Value("${weather.url}") final String weatherServiceUrl, @Value("${weather.api_secret}") final String weatherServiceApiKey) {
        this.restTemplate = restTemplate;
        this.weatherServiceUrl = weatherServiceUrl;
        this.weatherServiceApiKey = weatherServiceApiKey;
    }

    public Optional<WeatherApiResponse> fetchCurrentWeather(String cityName, String countryCode) {
        log.info("WeatherClient: fetch current weather for " + cityName + ", " + countryCode);
        String url = String.format("%s/data/2.5/weather?q=%s,%s&appid=%s", weatherServiceUrl, cityName, countryCode, weatherServiceApiKey);

        try {
            return Optional.ofNullable(restTemplate.getForObject(url, WeatherApiResponse.class));
        } catch (RestClientException e) {
            return Optional.empty();
        }
    }
}
