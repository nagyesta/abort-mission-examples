package com.github.nagyesta.abortmission.examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nagyesta.abortmission.booster.jupiter.annotation.LaunchAbortArmed;
import com.github.nagyesta.abortmission.core.annotation.MissionDependencies;
import me.adamcraftmaster.exceptions.JSONGetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

// Add Abort-Mission annotation
@LaunchAbortArmed
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WeatherRepositoryTest {

    private final String API_KEY = System.getProperty("API_KEY", "null");

    @Test
    public void testGetCurrentWeatherShouldThrowExceptionWhenApiKeyIsMissing() throws Exception {
        //given
        final WeatherRepository underTest = new WeatherRepository(null, new ObjectMapper().reader());

        //when
        Assertions.assertThrows(JSONGetException.class, () -> underTest.getCurrentWeather("London"));

        //then + exception
    }

    @Test
    @MissionDependencies("api-key")
    public void testGetCurrentWeatherShouldReturnCurrentHumidityWhenApiKeyIsPresent() throws Exception {
        //given
        final WeatherRepository underTest = new WeatherRepository(API_KEY, new ObjectMapper().reader());

        //when
        final Weather actual = underTest.getCurrentWeather("London");

        //then
        Assertions.assertTrue(actual.getHumidity() > 0, "Humidity should be greater than 0");
    }

    @Test
    @Tag("end-to-end")
    public void testGetCurrentWeatherShouldReturnCurrentTemperatureWhenApiKeyIsPresent() throws Exception {
        //given
        final WeatherRepository underTest = new WeatherRepository(API_KEY, new ObjectMapper().reader());

        //when
        final Weather actual = underTest.getCurrentWeather("London");

        //then
        Assertions.assertTrue(actual.getTemperature() > -20.0, "Temperature should be greater than -20.0°C");
        Assertions.assertTrue(actual.getTemperature() < 50.0, "Temperature should be less than 50.0°C");
    }

    @Test
    @Tag("end-to-end")
    public void testGetCurrentWeatherShouldReturnCurrentWindSpeedWhenApiKeyIsPresent() throws Exception {
        //given
        final WeatherRepository underTest = new WeatherRepository(API_KEY, new ObjectMapper().reader());

        //when
        final Weather actual = underTest.getCurrentWeather("London");

        //then
        Assertions.assertTrue(actual.getWindSpeed() > 0.0, "Wind speed should be greater than 0.0 km/h");
        Assertions.assertTrue(actual.getWindSpeed() < 100.0, "Wind speed should be less than 100.0 km/h");
    }

    @Test
    public void testGetCurrentWeatherShouldSuccessfullyMapValueWhenFetchReturnsPreparedValue() throws Exception {
        //given
        final WeatherRepository underTest = new WeatherRepository(API_KEY, new ObjectMapper().reader()) {
            @Override
            protected String fetchConditions(String region) throws JSONGetException {
                try (final InputStream stream = getClass().getResourceAsStream("/london.json")) {
                    return new String(Objects.requireNonNull(stream).readAllBytes(), StandardCharsets.UTF_8);
                } catch (IOException e) {
                    throw new JSONGetException(e.getMessage());
                }
            }
        };

        //when
        final Weather actual = underTest.getCurrentWeather("London");

        //then
        Assertions.assertEquals(19.0, actual.getTemperature());
        Assertions.assertEquals("WNW", actual.getWindDirection());
        Assertions.assertEquals(13.0, actual.getWindSpeed());
        Assertions.assertEquals(68, actual.getHumidity());
        Assertions.assertTrue(actual.isDay());
    }
}
