package pl.maciejbadziak.appweather.weatherarchive.exception;

import java.io.Serial;

public class InvalidCoordinateException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidCoordinateException(final String errorMessage) {
        super(errorMessage);
    }
}
