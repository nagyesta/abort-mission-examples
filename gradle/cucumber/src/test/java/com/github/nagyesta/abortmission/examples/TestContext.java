package com.github.nagyesta.abortmission.examples;

public class TestContext {

    public final String API_KEY = System.getProperty("API_KEY", "null");

    private WeatherRepository underTest;

    private Weather response;

    private Exception exception;

    public WeatherRepository getUnderTest() {
        return underTest;
    }

    public TestContext setUnderTest(WeatherRepository underTest) {
        this.underTest = underTest;
        return this;
    }

    public Weather getResponse() {
        return response;
    }

    public TestContext setResponse(Weather response) {
        this.response = response;
        return this;
    }

    public Exception getException() {
        return exception;
    }

    public TestContext setException(Exception exception) {
        this.exception = exception;
        return this;
    }
}
