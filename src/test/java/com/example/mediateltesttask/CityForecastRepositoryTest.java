package com.example.mediateltesttask;

import com.example.mediateltesttask.model.CityForecast;
import com.example.mediateltesttask.repository.CityForecastRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.mediateltesttask.TestData.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringTestConfiguration.class})
@DataJpaTest
public class CityForecastRepositoryTest {
    @Autowired
    private CityForecastRepository repository;

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void shouldSaveAndFetchCityForecast() {
        List<CityForecast> expected = new ArrayList<>(Arrays.asList(TOMORROW_CITY_FORECAST,SECOND_DAY_CITY_FORECAST, THIRD_DAY_CITY_FORECAST));
        repository.saveAll(FULL_BD);
        List<CityForecast> actualForecasts = repository.getForecastsByThreeDays(MOSCOW_LON, MOSCOW_LAT, START_AT_TOMORROW, END_AT_THIRD_DAY);
        Assertions.assertArrayEquals(expected.toArray(),actualForecasts.toArray());
    }
}