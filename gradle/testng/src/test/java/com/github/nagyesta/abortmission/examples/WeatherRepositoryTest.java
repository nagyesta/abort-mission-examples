package com.github.nagyesta.abortmission.examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nagyesta.abortmission.booster.testng.annotation.LaunchAbortArmed;
import com.github.nagyesta.abortmission.booster.testng.listener.AbortMissionListener;
import com.github.nagyesta.abortmission.core.annotation.MissionDependencies;
import me.adamcraftmaster.exceptions.JSONGetException;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

// Add Abort-Mission annotation
@LaunchAbortArmed
// Add listener
@Listeners(AbortMissionListener.class)
public class WeatherRepositoryTest {

    private final String API_KEY = System.getProperty("API_KEY", "null");

    @Test
    public void testGetCurrentWeatherShouldThrowExceptionWhenApiKeyIsMissing() throws Exception {
        //given
        final WeatherRepository underTest = new WeatherRepository(null, new ObjectMapper().reader());

        //when
        Assert.assertThrows(JSONGetException.class, () -> underTest.getCurrentWeather("London"));

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
        Assert.assertTrue(actual.getHumidity() > 0, "Humidity should be greater than 0");
    }

    @Test(groups = "end-to-end")
    public void testGetCurrentWeatherShouldReturnCurrentTemperatureWhenApiKeyIsPresent() throws Exception {
        //given
        final WeatherRepository underTest = new WeatherRepository(API_KEY, new ObjectMapper().reader());

        //when
        final Weather actual = underTest.getCurrentWeather("London");

        //then
        Assert.assertTrue(actual.getTemperature() > -20.0, "Temperature should be greater than -20.0°C");
        Assert.assertTrue(actual.getTemperature() < 50.0, "Temperature should be less than 50.0°C");
    }

    @Test(groups = "end-to-end")
    public void testGetCurrentWeatherShouldReturnCurrentWindSpeedWhenApiKeyIsPresent() throws Exception {
        //given
        final WeatherRepository underTest = new WeatherRepository(API_KEY, new ObjectMapper().reader());

        //when
        final Weather actual = underTest.getCurrentWeather("London");

        //then
        Assert.assertTrue(actual.getWindSpeed() > 0.0, "Wind speed should be greater than 0.0 km/h");
        Assert.assertTrue(actual.getWindSpeed() < 100.0, "Wind speed should be less than 100.0 km/h");
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
        Assert.assertEquals(actual.getTemperature(), 19.0);
        Assert.assertEquals(actual.getWindDirection(), "WNW");
        Assert.assertEquals(actual.getWindSpeed(), 13.0);
        Assert.assertEquals(actual.getHumidity(), 68);
        Assert.assertTrue(actual.isDay());
    }
}
