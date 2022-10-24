package com.example.mediateltesttask.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApiResponse {
    @JsonProperty("coord")
    private Coord coord;
    @JsonProperty("main")
    private Main main;

    public WeatherApiResponse() {
    }

    public Coord getCoord() {
        return coord;
    }

    public Main getMain() {
        return main;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Coord {
        private String lon;
        private String lat;

        public Coord() {
        }

        public String getLon() {
            return lon;
        }

        public String getLat() {
            return lat;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Main {
        private float temp;

        public Main() {
        }

        public float getTemp() {
            return temp;
        }
    }
}
