
package com.example.mediateltesttask.web;

import com.example.mediateltesttask.excaption.AppException;
import com.example.mediateltesttask.model.CityForecast;
import com.example.mediateltesttask.service.CityForecastService;
import com.example.mediateltesttask.to.TemperatureResponse;
import com.example.mediateltesttask.to.WeatherApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ForecastController {
    private final CityForecastService cityForecastService;

    @Autowired
    public ForecastController(CityForecastService cityForecastService) {
        this.cityForecastService = cityForecastService;
    }

    @GetMapping(value = "temperature")
    public TemperatureResponse getTemp(@RequestParam String cityName, @RequestParam String countryCode) throws AppException {
        WeatherApiResponse currentWeather = cityForecastService.getCurrentTemperature(cityName, countryCode);
        CityForecast forecastWithMinTemp = cityForecastService.checkInDbOrLoadNewForecast(currentWeather.getCoord().getLon(), currentWeather.getCoord().getLat());
        return new TemperatureResponse(currentWeather.getMain().getTemp(), forecastWithMinTemp.getTemperature());
    }
}
