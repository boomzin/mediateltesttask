package com.example.mediateltesttask.service;

import com.example.mediateltesttask.excaption.AppException;
import com.example.mediateltesttask.model.CityForecast;
import com.example.mediateltesttask.repository.CityForecastRepository;
import com.example.mediateltesttask.to.ForecastApiResponse;
import com.example.mediateltesttask.to.ForecastApiResponse.Forecast;
import com.example.mediateltesttask.to.WeatherApiResponse;
import com.example.mediateltesttask.util.ForecastClient;
import com.example.mediateltesttask.util.WeatherClient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CityForecastService {

    private final CityForecastRepository cityForecastRepository;
    private final ForecastClient forecastClient;

    private final WeatherClient weatherClient;

    public CityForecastService(CityForecastRepository cityForecastRepository, WeatherClient weatherClient, ForecastClient forecastClient) {
        this.cityForecastRepository = cityForecastRepository;
        this.weatherClient = weatherClient;
        this.forecastClient = forecastClient;
    }

    public WeatherApiResponse getCurrentTemperature(String cityName, String countryCode) throws AppException {
        Optional<WeatherApiResponse> optionalWeather = weatherClient.fetchCurrentWeather(cityName, countryCode);
        if (optionalWeather.isPresent()) {
            return optionalWeather.get();
        } else {
            throw new AppException("Город не найден");
        }
    }


    public CityForecast checkInDbOrLoadNewForecast(String longitude, String latitude) throws AppException {

        long startAtTomorrow = LocalDate.now().atStartOfDay().plusDays(1).toInstant(ZoneOffset.UTC).getEpochSecond();
        long startAtThirdDay = LocalDate.now().atStartOfDay().plusDays(3).toInstant(ZoneOffset.UTC).getEpochSecond();
        long endAtThirdDay = LocalDate.now().atTime(LocalTime.MAX).plusDays(3).toInstant(ZoneOffset.UTC).getEpochSecond();

        List<CityForecast> forecastsFromDb = cityForecastRepository.getForecastsByThreeDays(longitude, latitude, startAtTomorrow, endAtThirdDay);
        if (forecastsFromDb.isEmpty()) {
            ForecastApiResponse forecastApiResponse = forecastClient.fetchForecasts(longitude, latitude).orElseThrow(()-> new AppException("Неверные координаты"));
            List<Forecast> newForecasts = forecastApiResponse.getForecasts();

            newForecasts.stream().filter(x -> x.getTimestamp() >= startAtTomorrow).forEach(x -> cityForecastRepository.save(new CityForecast(null, longitude, latitude, x.getTimestamp(), x.getMain().getTemp())));

            return getCityForecastWithMinTemp(longitude, latitude, startAtTomorrow, endAtThirdDay, newForecasts);
        } else {
            Comparator<CityForecast> compareByTimestamp = Comparator.comparingLong(CityForecast::getTimestampUTC);
            CityForecast forecastLatestInDb = forecastsFromDb.stream().max(compareByTimestamp).get();
            long latestTimeInBd = forecastLatestInDb.getTimestampUTC();

            if (latestTimeInBd < startAtThirdDay) {
                ForecastApiResponse forecastApiResponse = forecastClient.fetchForecasts(longitude, latitude).orElseThrow(()-> new AppException("Неверные координаты"));
                List<ForecastApiResponse.Forecast> newForecasts = forecastApiResponse.getForecasts();


                return getCityForecastWithMinTemp(longitude, latitude, startAtTomorrow, endAtThirdDay, newForecasts);
            } else {
                Comparator<CityForecast> compareByTemp = (o1, o2) -> Float.compare(o1.getTemperature(), o2.getTemperature());
                return forecastsFromDb.stream().filter(x -> x.getTimestampUTC() >= startAtTomorrow && x.getTimestampUTC() < endAtThirdDay).min(compareByTemp).get();
            }
        }
    }

    private CityForecast getCityForecastWithMinTemp(String longitude, String latitude, long startAtTomorrow, long endAtThirdDay, List<Forecast> newForecasts) {
        Comparator<Forecast> compareByTemp = (o1, o2) -> Float.compare(o1.getMain().getTemp(), o2.getMain().getTemp());
        Forecast forecastWithMinTemp = newForecasts.stream().filter(x -> x.getTimestamp() >= startAtTomorrow && x.getTimestamp() < endAtThirdDay).min(compareByTemp).get();

        return new CityForecast(null, longitude, latitude, forecastWithMinTemp.getTimestamp(), forecastWithMinTemp.getMain().getTemp());
    }
}
