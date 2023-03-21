package pl.maciejbadziak.appweather.weatherarchive.adapter.in.rest.errorhandling;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.maciejbadziak.appweather.common.resource.ErrorResource;
import pl.maciejbadziak.appweather.weatherarchive.exception.InvalidCoordinateException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidCoordinateException.class)
    public ResponseEntity<ErrorResource> handleInvalidCoordinateException(final InvalidCoordinateException exception) {
        return new ResponseEntity<>(ErrorResource.builder()
                .title(BAD_REQUEST.getReasonPhrase())
                .message(exception.getMessage())
                .build(), BAD_REQUEST);
    }
}
