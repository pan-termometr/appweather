package pl.maciejbadziak.appweather.weatherarchive.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.maciejbadziak.appweather.weatherarchive.exception.InvalidCoordinateException;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.of;

class CoordinatesValidatorServiceTest {

    private static final String LATITUDE_INVALID_ERROR_MSG = "Requested latitude coordinate: [%f] is not valid. ";
    private static final String LONGITUDE_INVALID_ERROR_MSG = "Requested longitude coordinate: [%f] is not valid. ";
    private static final String TWO_COORDINATES_INVALID_ERROR_MSG = LATITUDE_INVALID_ERROR_MSG + LONGITUDE_INVALID_ERROR_MSG;

    @ParameterizedTest
    @MethodSource("validCoordinates")
    void shouldValidateValidCoordinates(final double latitude, final double longitude) {
        // given
        // when
        // then
        assertDoesNotThrow(() -> CoordinatesValidatorService.validate(latitude, longitude));
    }

    @ParameterizedTest
    @MethodSource("invalidCoordinates")
    void shouldThrowInvalidCoordinateExceptionForInvalidCoordinates(final double latitude, final double longitude, final String errorMessage) {
        // given
        // when
        // then
        assertThrows(
                InvalidCoordinateException.class,
                () -> CoordinatesValidatorService.validate(latitude, longitude),
                String.format(errorMessage, latitude, longitude));
    }

    private static Stream<Arguments> validCoordinates() {
        return Stream.of(
                of(-90.00, -180.00),
                of(90, 180),
                of(0, 0),
                of(13.131313, 13.131313)
        );
    }

    private static Stream<Arguments> invalidCoordinates() {
        return Stream.of(
                of(90.01, 0, LATITUDE_INVALID_ERROR_MSG),
                of(0, 180.01, LONGITUDE_INVALID_ERROR_MSG),
                of(-90.001, -180.001, TWO_COORDINATES_INVALID_ERROR_MSG)
        );
    }

}
