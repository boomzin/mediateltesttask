package com.example.mediateltesttask.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastApiResponse {
    @JsonProperty("list")
    private List<Forecast> forecasts;

    public ForecastApiResponse() {
    }


    public List<Forecast> getForecasts() {
        return forecasts;
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Forecast {
        @JsonProperty("dt")
        private int timestamp;
        private Main main;

        public Forecast() {
        }

        public int getTimestamp() {
            return timestamp;
        }

        public Main getMain() {
            return main;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Main {
        @JsonProperty("temp")
        float temp;

        public Main() {
        }

        public float getTemp() {
            return temp;
        }
    }
}
