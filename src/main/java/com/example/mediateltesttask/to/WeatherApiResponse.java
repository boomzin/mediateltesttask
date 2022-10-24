package com.example.mediateltesttask.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApiResponse {
    @JsonProperty("coord")
    private Coord coord;
    @JsonProperty("main")
    private Main main;

    public WeatherApiResponse() {
    }

    public WeatherApiResponse(Coord coord, Main main) {
        this.coord = coord;
        this.main = main;
    }

    public Coord getCoord() {
        return coord;
    }

    public Main getMain() {
        return main;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeatherApiResponse response = (WeatherApiResponse) o;
        return coord.equals(response.coord) && main.equals(response.main);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coord, main);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Coord {
        private String lon;
        private String lat;

        public Coord() {
        }

        public Coord(String lon, String lat) {
            this.lon = lon;
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public String getLat() {
            return lat;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Main {
        private float temp;

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
