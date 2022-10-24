package com.example.mediateltesttask;

import com.example.mediateltesttask.model.CityForecast;
import com.example.mediateltesttask.to.ForecastApiResponse;
import com.example.mediateltesttask.to.WeatherApiResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestData {

    static final float TODAY_TEMP = 33.33f;
    static final float MIN_TEMP = 20.33f;

    static final String MOSCOW_LON = "37.6156";
    static final String MOSCOW_LAT = "37.6156";
    static final long TODAY = LocalDate.now().toEpochSecond(LocalTime.now(), ZoneOffset.UTC);
    static final long START_AT_TOMORROW = LocalDate.now().atStartOfDay().plusDays(1).toInstant(ZoneOffset.UTC).getEpochSecond();
    static final long START_AT_SECOND_DAY = LocalDate.now().atStartOfDay().plusDays(2).toInstant(ZoneOffset.UTC).getEpochSecond();
    static final long START_AT_THIRD_DAY = LocalDate.now().atStartOfDay().plusDays(3).toInstant(ZoneOffset.UTC).getEpochSecond();
    static final long END_AT_THIRD_DAY = LocalDate.now().atTime(LocalTime.MAX).plusDays(3).toInstant(ZoneOffset.UTC).getEpochSecond();

    static final CityForecast TODAY_CITY_FORECAST = new CityForecast(null, MOSCOW_LON, MOSCOW_LAT, TODAY, TODAY_TEMP);
    static final CityForecast TOMORROW_CITY_FORECAST = new CityForecast(null, MOSCOW_LON, MOSCOW_LAT, START_AT_TOMORROW, 35.33f);
    static final CityForecast SECOND_DAY_CITY_FORECAST = new CityForecast(null, MOSCOW_LON, MOSCOW_LAT, START_AT_SECOND_DAY, MIN_TEMP);
    static final CityForecast FORECAST_WITH_MIN_TEMP_FROM_API = new CityForecast(null, MOSCOW_LON, MOSCOW_LAT, START_AT_SECOND_DAY, MIN_TEMP);
    static final CityForecast THIRD_DAY_CITY_FORECAST = new CityForecast(null, MOSCOW_LON, MOSCOW_LAT, START_AT_THIRD_DAY, 34.33f);
    static final CityForecast THIRD_DAY_CITY_FORECAST2 = new CityForecast(null, MOSCOW_LON, MOSCOW_LAT, END_AT_THIRD_DAY, 30.33f);

    static final ForecastApiResponse.Forecast TODAY_FORECAST = new ForecastApiResponse.Forecast(TODAY, new ForecastApiResponse.Main(TODAY_TEMP));
    static final ForecastApiResponse.Forecast TOMORROW_FORECAST = new ForecastApiResponse.Forecast(START_AT_TOMORROW, new ForecastApiResponse.Main(35.33f));
    static final ForecastApiResponse.Forecast SECOND_DAY_FORECAST = new ForecastApiResponse.Forecast(START_AT_SECOND_DAY, new ForecastApiResponse.Main(MIN_TEMP));
    static final ForecastApiResponse.Forecast THIRD_DAY_FORECAST = new ForecastApiResponse.Forecast(START_AT_THIRD_DAY, new ForecastApiResponse.Main(34.33f));
    static final ForecastApiResponse.Forecast THIRD_DAY_FORECAST1 = new ForecastApiResponse.Forecast(END_AT_THIRD_DAY, new ForecastApiResponse.Main(30.33f));

    static final WeatherApiResponse WEATHER_API_RESPONSE = new WeatherApiResponse(new WeatherApiResponse.Coord(MOSCOW_LON, MOSCOW_LAT), new WeatherApiResponse.Main(TODAY_TEMP));

    static final List<CityForecast> EMPTY_BD = Collections.emptyList();
    static final List<CityForecast> FULL_BD = new ArrayList<>(Arrays.asList(TODAY_CITY_FORECAST, TOMORROW_CITY_FORECAST, SECOND_DAY_CITY_FORECAST, THIRD_DAY_CITY_FORECAST, THIRD_DAY_CITY_FORECAST2));
    static final List<CityForecast> HALF_BD = new ArrayList<>(Arrays.asList(TODAY_CITY_FORECAST, TOMORROW_CITY_FORECAST));

    static final ForecastApiResponse FORECAST_API_RESPONSE = new ForecastApiResponse(new ArrayList<>(Arrays.asList(TODAY_FORECAST, TOMORROW_FORECAST, SECOND_DAY_FORECAST, THIRD_DAY_FORECAST, THIRD_DAY_FORECAST1)));
}
