package pl.maciejbadziak.appweather.weatherarchive.adapter.out.rest.exception;

import org.springframework.http.HttpStatusCode;

import java.io.Serial;

public class WeatherApiException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Cannot retrieve weather information. Response code: %d ";

    @Serial
    private static final long serialVersionUID = 1L;

    public WeatherApiException(final HttpStatusCode statusCode) {
        super(String.format(ERROR_MESSAGE, statusCode.value()));
    }
}
