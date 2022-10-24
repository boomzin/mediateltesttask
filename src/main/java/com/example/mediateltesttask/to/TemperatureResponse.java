package com.example.mediateltesttask.to;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TemperatureResponse {
    @JsonProperty("current temperature")
    private float currentTemperature;
    @JsonProperty("minimal temperature by three days")
    private float minTempForecast;

    public TemperatureResponse(float currentTemperature, float minTempForecast) {
        this.currentTemperature = currentTemperature;
        this.minTempForecast = minTempForecast;
    }
}
