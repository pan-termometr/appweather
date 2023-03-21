package pl.maciejbadziak.appweather.weatherarchive.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.maciejbadziak.appweather.weatherarchive.exception.InvalidCoordinateException;

@Service
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoordinatesValidatorService {

    private static final double MIN_LATITUDE = -90f;
    private static final double MAX_LATITUDE = 90f;
    private static final double MIN_LONGITUDE = -180f;
    private static final double MAX_LONGITUDE = 180f;
    private static final String LATITUDE_ERROR_MSG = "Requested latitude coordinate: [%f] is not valid. ";
    private static final String LONGITUDE_ERROR_MSG = "Requested longitude coordinate: [%f] is not valid. ";

    public static void validate(final double latitude, final double longitude) throws InvalidCoordinateException{
        final StringBuilder errorMessage = new StringBuilder();

        validateLatitude(latitude, errorMessage);
        validateLongitude(longitude, errorMessage);

        if(!errorMessage.toString().isBlank()) {
            throw new InvalidCoordinateException(errorMessage.toString());
        }
    }

    private static void validateLatitude(final double latitude, final StringBuilder errorMessage) {
        if(isLatitudeOutOfRange(latitude)) {
            errorMessage.append(String.format(LATITUDE_ERROR_MSG, latitude));
        }
    }

    private static void validateLongitude(final double longitude, final StringBuilder errorMessage) {
        if(isLongitudeOutOfRange(longitude)) {
            errorMessage.append(String.format(LONGITUDE_ERROR_MSG, longitude));
        }
    }

    private static boolean isLatitudeOutOfRange(final double latitude) {
        return latitude < MIN_LATITUDE || latitude > MAX_LATITUDE;
    }

    private static boolean isLongitudeOutOfRange(final double longitude) {
        return longitude < MIN_LONGITUDE || longitude > MAX_LONGITUDE;
    }
}
