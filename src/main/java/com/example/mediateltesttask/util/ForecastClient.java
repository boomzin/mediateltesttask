package com.example.mediateltesttask.util;

import com.example.mediateltesttask.to.ForecastApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class ForecastClient {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ForecastClient.class);
    private final RestTemplate restTemplate;
    private final String weatherServiceUrl;
    private final String weatherServiceApiKey;

    @Autowired
    public ForecastClient(final RestTemplate restTemplate, @Value("${weather.url}") final String weatherServiceUrl, @Value("${weather.api_secret}") final String weatherServiceApiKey) {
        this.restTemplate = restTemplate;
        this.weatherServiceUrl = weatherServiceUrl;
        this.weatherServiceApiKey = weatherServiceApiKey;
    }

    public Optional<ForecastApiResponse> fetchForecasts(String longitude, String latitude) {
        log.info("WeatherClient: fetch forecasts by 5 days, step 3 hours for " + longitude + ", " + latitude);
        String url = String.format("%s/data/2.5/forecast?lat=%s&lon=%s&appid=%s", weatherServiceUrl, latitude, longitude, weatherServiceApiKey);

        try {
            return Optional.ofNullable(restTemplate.getForObject(url, ForecastApiResponse.class));
        } catch (RestClientException e) {
            return Optional.empty();
        }
    }
}
