package pl.maciejbadziak.appweather.weatherarchive.adapter.out.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.AssertionFailedError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import pl.maciejbadziak.appweather.weatherarchive.adapter.out.rest.exception.WeatherApiException;
import pl.maciejbadziak.appweather.weatherarchive.domain.DailyReport;
import pl.maciejbadziak.appweather.weatherarchive.domain.WeatherArchive;
import reactor.core.publisher.Mono;

import java.net.URI;

import static java.lang.String.format;
import static java.time.LocalDate.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.reactive.function.client.ClientResponse.create;
import static pl.maciejbadziak.appweather.weatherarchive.testdata.WeatherArchiveApiResourceTestData.weatherArchiveApiResource;
import static pl.maciejbadziak.appweather.weatherarchive.testdata.WeatherArchiveTestData.weatherArchive;
import static reactor.core.publisher.Mono.just;

@ExtendWith(MockitoExtension.class)
class GetWeatherArchiveByLocationAdapterTest {

    private static final double LATITUDE = 13.13;
    private static final double LONGITUDE = -13.13;
    private static final String URI_PATH = "/v1/archive";
    private static final String QUERY = "latitude=13.13&longitude=-13.13&start_date=2023-03-14&end_date=2023-03-20&hourly=precipitation&daily=sunrise,sunset&timezone=Europe/Berlin";
    private static final String SERIALIZATION_ERROR = "Cannot serialize JSON";
    private static final String CORRECT_REQUEST = "Correct request";
    private static final String ERROR_PROCESSED = "Error processed";
    private static final String ERROR_MESSAGE = "Cannot retrieve weather information. Response code: %d ";


    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Mock
    private transient ExchangeFunction exchangeFunctionMock;

    @Mock
    private transient WeatherArchiveApiResourceAssembler assemblerMock;

    private transient GetWeatherArchiveByLocationAdapter adapter;

    @BeforeEach
    void init() {
        final WebClient webClient = WebClient.builder()
                .exchangeFunction(exchangeFunctionMock)
                .build();
        adapter = new GetWeatherArchiveByLocationAdapter(webClient, assemblerMock);
    }

    @Test
    void shouldRetrieveWeatherArchiveByLocation() {
        // given
        when(exchangeFunctionMock.exchange(any(ClientRequest.class))).thenReturn(buildSuccessfulResponse());
        when(assemblerMock.assembleToWeatherArchive(any())).thenReturn(weatherArchive());

        // when
        final WeatherArchive result = adapter.getWeeklyReport(LATITUDE, LONGITUDE);

        // then
        assertThat(result.getDailyReports()).extracting(
                DailyReport::getDate,
                DailyReport::getSunrise,
                DailyReport::getSunset,
                DailyReport::getAvgPrecipitation
        ).containsExactly(
                tuple(
                        of(2023, 3, 20),
                        result.getDailyReports().get(0).getSunrise(),
                        result.getDailyReports().get(0).getSunset(),
                        result.getDailyReports().get(0).getAvgPrecipitation()),
                tuple(
                        of(2023, 3, 19),
                        result.getDailyReports().get(1).getSunrise(),
                        result.getDailyReports().get(1).getSunset(),
                        result.getDailyReports().get(1).getAvgPrecipitation())
        );
    }

    @Test
    void verifyUsedPathAndQuery() {
        // given
        when(exchangeFunctionMock.exchange(any(ClientRequest.class))).thenReturn(buildSuccessfulResponse());
        when(assemblerMock.assembleToWeatherArchive(any())).thenReturn(weatherArchive());

        // when
        adapter.getWeeklyReport(LATITUDE, LONGITUDE);

        // then
        final var clientRequestCaptor = forClass(ClientRequest.class);
        verify(exchangeFunctionMock).exchange(clientRequestCaptor.capture());
        assertThat(clientRequestCaptor.getValue()).as(CORRECT_REQUEST)
                .returns(GET, ClientRequest::method)
                .extracting(ClientRequest::url)
                .returns(URI_PATH, URI::getPath)
                .returns(QUERY, URI::getQuery);
    }

    @ParameterizedTest
    @MethodSource("dataErrors")
    void verifyErrorProcessing(
            final Mono<ClientResponse> response,
            final Class<? extends Throwable> errorClass,
            final String message) {
        // given
        when(exchangeFunctionMock.exchange(any(ClientRequest.class))).thenReturn(response);

        // when
        // then
        assertThatExceptionOfType(errorClass).as(ERROR_PROCESSED)
                .isThrownBy(() -> adapter.getWeeklyReport(LATITUDE, LONGITUDE))
                .withMessage(message);
    }

    private static Object[][] dataErrors() {
        return new Object[][] {
                { buildNoContentResponse(), WeatherApiException.class, format(ERROR_MESSAGE, NO_CONTENT.value()) },
                { buildInternalServerErrorResponse(), WeatherApiException.class, format(ERROR_MESSAGE, INTERNAL_SERVER_ERROR.value()) },
        };
    }

    private static Mono<ClientResponse> buildNoContentResponse() {
        return just(
                create(NO_CONTENT)
                        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .build());
    }

    private static Object buildInternalServerErrorResponse() {
        return just(
                create(INTERNAL_SERVER_ERROR)
                        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .build());
    }

    private static Mono<ClientResponse> buildSuccessfulResponse() {
        return just(
                create(OK)
                        .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .body(toJson(weatherArchiveApiResource()))
                        .build()
        );
    }

    private static String toJson(final Object value) {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (final JsonProcessingException e) {
            throw new AssertionFailedError(SERIALIZATION_ERROR);
        }
    }
}
