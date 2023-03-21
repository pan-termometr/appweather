package pl.maciejbadziak.appweather.weatherarchive.adapter.out.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import pl.maciejbadziak.appweather.weatherarchive.adapter.out.rest.exception.WeatherApiException;
import pl.maciejbadziak.appweather.weatherarchive.adapter.out.rest.resources.WeatherArchiveApiResource;
import pl.maciejbadziak.appweather.weatherarchive.domain.WeatherArchive;
import pl.maciejbadziak.appweather.weatherarchive.port.GetWeatherArchiveByLocationPort;
import reactor.core.publisher.Mono;

import java.net.URI;

import static java.time.LocalDate.now;
import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Component
@RequiredArgsConstructor
public class GetWeatherArchiveByLocationAdapter implements GetWeatherArchiveByLocationPort {

    private static final long ARCHIVED_DAYS = 7;

    @Qualifier("weather-archive")
    private final WebClient webClient;

    private final WeatherArchiveApiResourceAssembler assembler;

    @Override
    public WeatherArchive getWeeklyReport(final double latitude, final double longitude) {
        final WeatherArchiveApiResource response = webClient.get()
                .uri(uri -> prepareUri(uri, latitude, longitude))
                .exchangeToMono(this::mapResponseToResource)
                .block();

        if(response == null) {
            throw new WeatherApiException(NO_CONTENT);
        }

        return assembler.assembleToWeatherArchive(requireNonNull(response));
    }

    private Mono<WeatherArchiveApiResource> mapResponseToResource(final ClientResponse response) {
        if(response.statusCode().is2xxSuccessful()) {
            return response.bodyToMono(new ParameterizedTypeReference<>() {
            });
        }

        if(response.statusCode().is5xxServerError()) {
            throw new WeatherApiException(INTERNAL_SERVER_ERROR);
        }

        return response.bodyToMono(String.class)
                .flatMap(body -> Mono.error(new WeatherApiException(response.statusCode())));
    }

    private URI prepareUri(final UriBuilder uri, final double latitude, final double longitude) {
        return uri.pathSegment()
                .pathSegment("v1")
                .pathSegment("archive")
                .queryParam("latitude", latitude)
                .queryParam("longitude", longitude)
                .queryParam("start_date", now().minusDays(ARCHIVED_DAYS - 1))
                .queryParam("end_date", now())
                .queryParam("hourly", "precipitation")
                .queryParam("daily", "sunrise,sunset")
                .queryParam("timezone", "Europe/Berlin")
                .build();
    }
}
