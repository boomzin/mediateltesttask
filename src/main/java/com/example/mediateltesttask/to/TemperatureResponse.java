package com.example.mediateltesttask.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class TemperatureResponse {
    @JsonProperty("current temperature")
    private float currentTemperature;
    @JsonProperty("minimal temperature by three days")
    private float minTempForecast;

    public TemperatureResponse(float currentTemperature, float minTempForecast) {
        this.currentTemperature = currentTemperature;
        this.minTempForecast = minTempForecast;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemperatureResponse that = (TemperatureResponse) o;
        return Float.compare(that.currentTemperature, currentTemperature) == 0 && Float.compare(that.minTempForecast, minTempForecast) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentTemperature, minTempForecast);
    }
}
