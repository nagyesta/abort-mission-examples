package com.github.nagyesta.abortmission.examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nagyesta.abortmission.booster.junit4.annotation.LaunchAbortArmed;
import com.github.nagyesta.abortmission.booster.junit4.support.LaunchAbortTestWatcher;
import com.github.nagyesta.abortmission.core.annotation.MissionDependencies;
import me.adamcraftmaster.exceptions.JSONGetException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.rules.TestRule;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

// HINT: Add Abort-Mission annotation
@LaunchAbortArmed
public class WeatherRepositoryTest {

    private final String API_KEY = System.getProperty("API_KEY", "null");

    // HINT: Make sure to use the LaunchAbortTestWatcher
    @Rule
    public TestRule watcher = new LaunchAbortTestWatcher(this.getClass());

    @Test(expected = JSONGetException.class)
    public void testGetCurrentWeatherShouldThrowExceptionWhenApiKeyIsMissing() throws Exception {
        //given
        final WeatherRepository underTest = new WeatherRepository(null, new ObjectMapper().reader());

        //when
        underTest.getCurrentWeather("London");

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
        Assert.assertTrue("Humidity should be greater than 0", actual.getHumidity() > 0);
    }

    @Test
    @Category(EndToEnd.class)
    public void testGetCurrentWeatherShouldReturnCurrentTemperatureWhenApiKeyIsPresent() throws Exception {
        //given
        final WeatherRepository underTest = new WeatherRepository(API_KEY, new ObjectMapper().reader());

        //when
        final Weather actual = underTest.getCurrentWeather("London");

        //then
        Assert.assertTrue("Temperature should be greater than -20.0°C", actual.getTemperature() > -20.0);
        Assert.assertTrue("Temperature should be less than 50.0°C", actual.getTemperature() < 50.0);
    }

    @Test
    @Category(EndToEnd.class)
    public void testGetCurrentWeatherShouldReturnCurrentWindSpeedWhenApiKeyIsPresent() throws Exception {
        //given
        final WeatherRepository underTest = new WeatherRepository(API_KEY, new ObjectMapper().reader());

        //when
        final Weather actual = underTest.getCurrentWeather("London");

        //then
        Assert.assertTrue("Wind speed should be greater than 0.0 km/h", actual.getWindSpeed() > 0.0);
        Assert.assertTrue("Wind speed should be less than 100.0 km/h", actual.getWindSpeed() < 100.0);
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
        Assert.assertEquals(19.0, actual.getTemperature(), 0.01);
        Assert.assertEquals("WNW", actual.getWindDirection());
        Assert.assertEquals(13.0, actual.getWindSpeed(), 0.01);
        Assert.assertEquals(68, actual.getHumidity());
        Assert.assertTrue(actual.isDay());
    }
}
