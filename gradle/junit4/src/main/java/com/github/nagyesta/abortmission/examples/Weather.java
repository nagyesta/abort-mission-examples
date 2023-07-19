package com.github.nagyesta.abortmission.examples;

import java.util.Objects;

public class Weather {

    double temperature;
    double windSpeed;
    String windDirection;
    int humidity;
    boolean isDay;

    public double getTemperature() {
        return temperature;
    }

    public Weather setTemperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public Weather setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
        return this;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public Weather setWindDirection(String windDirection) {
        this.windDirection = windDirection;
        return this;
    }

    public int getHumidity() {
        return humidity;
    }

    public Weather setHumidity(int humidity) {
        this.humidity = humidity;
        return this;
    }

    public boolean isDay() {
        return isDay;
    }

    public Weather setDay(boolean day) {
        isDay = day;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Weather weather)) {
            return false;
        }
        return temperature == weather.temperature && windSpeed == weather.windSpeed && humidity == weather.humidity && isDay == weather.isDay;
    }

    @Override
    public int hashCode() {
        return Objects.hash(temperature, windSpeed, humidity, isDay);
    }
}
