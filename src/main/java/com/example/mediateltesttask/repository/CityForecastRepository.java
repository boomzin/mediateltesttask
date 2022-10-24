package com.example.mediateltesttask.repository;

import com.example.mediateltesttask.model.CityForecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CityForecastRepository extends JpaRepository<CityForecast, Integer> {

    @Query("SELECT c FROM CityForecast c WHERE c.longitude = :lon AND c.latitude = :lat AND (c.timestampUTC >= :start OR c.timestampUTC < :end)")
    List<CityForecast> getForecastsByThreeDays(String lon, String lat, long start, long end);
}
