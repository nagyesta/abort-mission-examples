package com.github.nagyesta.abortmission.examples;

import com.fasterxml.jackson.databind.ObjectReader;
import me.adamcraftmaster.exceptions.JSONGetException;
import me.adamcraftmaster.schema.currentweather.CurrentWeather;
import me.adamcraftmaster.utils.JSONParserUtil;

import java.io.IOException;
import java.text.MessageFormat;

public class WeatherRepository {

    private static final String URL_FORMAT = "https://api.weatherapi.com/v1/current.json?key={0}&q={1}&aqi=no";
    private final ObjectReader objectReader;
    private final String apiKey;

    public WeatherRepository(String apiKey, ObjectReader objectReader) {
        this.apiKey = apiKey;
        this.objectReader = objectReader;
    }

    public Weather getCurrentWeather(String region) throws Exception {
        var conditions = getConditions(region);
        var temperature = conditions.getCurrent().getTempC();
        var humidity = conditions.getCurrent().getHumidity();
        var isDay = conditions.getCurrent().getIsDay() == 1;
        var windSpeed = conditions.getCurrent().getWindKph();
        var windDirection = conditions.getCurrent().getWindDir();
        return new Weather()
                .setTemperature(temperature)
                .setHumidity(humidity)
                .setDay(isDay)
                .setWindSpeed(windSpeed)
                .setWindDirection(windDirection);
    }

    protected String fetchConditions(String region) throws JSONGetException {
        return JSONParserUtil.urlToJson(MessageFormat.format(URL_FORMAT, apiKey, region));
    }

    private CurrentWeather getConditions(String region) throws Exception {
        final String jsonString = fetchConditions(region);
        return mapToConditions(jsonString);
    }

    private CurrentWeather mapToConditions(String jsonString) throws IOException {
        return objectReader.readValue(jsonString, CurrentWeather.class);
    }
}
