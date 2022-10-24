package com.example.mediateltesttask.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ForecastApiResponse {
    @JsonProperty("list")
    private List<Forecast> forecasts;

    public ForecastApiResponse() {
    }

    public ForecastApiResponse(List<Forecast> forecasts) {
        this.forecasts = forecasts;
    }

    public List<Forecast> getForecasts() {
        return forecasts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForecastApiResponse that = (ForecastApiResponse) o;
        return forecasts.equals(that.forecasts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(forecasts);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Forecast {
        @JsonProperty("dt")
        private long timestamp;
        private Main main;

        public Forecast() {
        }

        public Forecast(long timestamp, Main main) {
            this.timestamp = timestamp;
            this.main = main;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public Main getMain() {
            return main;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Forecast forecast = (Forecast) o;
            return timestamp == forecast.timestamp && main.equals(forecast.main);
        }

        @Override
        public int hashCode() {
            return Objects.hash(timestamp, main);
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Main {
        @JsonProperty("temp")
        float temp;

        public Main() {
        }

        public Main(float temp) {
            this.temp = temp;
        }

        public float getTemp() {
            return temp;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Main main = (Main) o;
            return Float.compare(main.temp, temp) == 0;
        }

        @Override
        public int hashCode() {
            return Objects.hash(temp);
        }
    }
}
