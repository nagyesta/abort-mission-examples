package com.github.nagyesta.abortmission.examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import me.adamcraftmaster.exceptions.JSONGetException;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class WeatherRepositoryStepDefs {

    private final TestContext context;

    public WeatherRepositoryStepDefs(TestContext context) {
        this.context = context;
    }

    @Given("a weather repository is created without API key")
    public void aWeatherRepositoryIsCreatedWithoutApiKey() {
        context.setUnderTest(new WeatherRepository(null, new ObjectMapper().reader()));
    }

    @Given("a weather repository is created with API key")
    public void aWeatherRepositoryIsCreatedWithAPIKey() {
        context.setUnderTest(new WeatherRepository(context.API_KEY, new ObjectMapper().reader()));
    }

    @Given("a weather repository is created without API key using prefetched data")
    public void aWeatherRepositoryIsCreatedWithoutAPIKeyUsingPrefetchedData() {
        context.setUnderTest(new WeatherRepository(null, new ObjectMapper().reader()) {
            @Override
            protected String fetchConditions(String region) throws JSONGetException {
                try (final InputStream stream = getClass().getResourceAsStream("/london.json")) {
                    return new String(Objects.requireNonNull(stream).readAllBytes(), StandardCharsets.UTF_8);
                } catch (IOException e) {
                    throw new JSONGetException(e.getMessage());
                }
            }
        });
    }

    @When("weather data is requested for London")
    public void weatherDataIsRequestedForLondon() {
        try {
            final Weather weather = context.getUnderTest().getCurrentWeather("London");
            context.setResponse(weather);
        } catch (Exception e) {
            context.setException(e);
        }
    }

    @Then("an exception is received")
    public void anExceptionIsReceived() {
        Assertions.assertNull(context.getResponse());
        Assertions.assertNotNull(context.getException());
    }

    @Then("temperature data is mapped")
    public void temperatureDataIsMapped() {
        Assertions.assertNull(context.getException());
        Assertions.assertTrue(context.getResponse().getTemperature() > -20.0, "Temperature should be greater than -20.0°C");
        Assertions.assertTrue(context.getResponse().getTemperature() < 50.0, "Temperature should be less than 50.0°C");
    }

    @Then("humidity data is mapped")
    public void humidityDataIsMapped() {
        Assertions.assertNull(context.getException());
        Assertions.assertTrue(context.getResponse().getHumidity() > 0, "Humidity should be greater than 0");
    }

    @Then("wind speed data is mapped")
    public void windSpeedDataIsMapped() {
        Assertions.assertNull(context.getException());
        Assertions.assertTrue(context.getResponse().getWindSpeed() > 0, "Wind speed should be greater than 0");
    }

    @Then("prefetched data is mapped")
    public void prefetchedDataIsMapped() {
        final Weather actual = context.getResponse();
        Assertions.assertEquals(19.0, actual.getTemperature());
        Assertions.assertEquals("WNW", actual.getWindDirection());
        Assertions.assertEquals(13.0, actual.getWindSpeed());
        Assertions.assertEquals(68, actual.getHumidity());
        Assertions.assertTrue(actual.isDay());
    }
}
