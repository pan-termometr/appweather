package pl.maciejbadziak.appweather.weatherarchive.adapter.in.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import pl.maciejbadziak.appweather.MockServerIntegrationTest;
import pl.maciejbadziak.appweather.weatherarchive.adapter.out.postgres.ArchiveRequestRepository;
import pl.maciejbadziak.appweather.weatherarchive.adapter.out.postgres.resources.RequestEntity;
import pl.maciejbadziak.appweather.weatherarchive.adapter.out.rest.resources.WeatherArchiveApiResource;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static java.lang.Double.parseDouble;
import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.hamcrest.Matchers.is;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.MediaType.APPLICATION_JSON;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.maciejbadziak.appweather.weatherarchive.testdata.WeatherArchiveApiResourceTestData.weatherArchiveApiResource;

class WeatherArchiveRestControllerIntegrationTest extends MockServerIntegrationTest {
    private static final String ENDPOINT_URL = "/weather-archive";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ArchiveRequestRepository repository;

    @BeforeEach
    void init() {
        repository.deleteAll();
    }

    @Test
    void shouldRetrieveWeatherArchiveResponseAndSaveRequestIntoDatabase() throws Exception {
        // given
        final String latitude = "13.13";
        final String longitude = "130.13";
        mockApiResponse();

        // when
        final ResultActions result = mvc.perform(get(ENDPOINT_URL)
                .param("latitude", latitude)
                .param("longitude", longitude));

        // then
        final RequestEntity savedRequest = repository.getReferenceById(1L);

        result.andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].date", is("2023-03-20")))
                .andExpect(jsonPath("$[0].sunrise", is("10:10")))
                .andExpect(jsonPath("$[0].sunset", is("22:22")))
                .andExpect(jsonPath("$[0].avgPrecipitation", is(1.4)))
                .andExpect(jsonPath("$[1].date", is("2023-03-19")))
                .andExpect(jsonPath("$[1].sunrise", is("10:11")))
                .andExpect(jsonPath("$[1].sunset", is("22:21")))
                .andExpect(jsonPath("$[1].avgPrecipitation", is(0.7)));
        Assertions.assertThat(savedRequest).extracting(
                RequestEntity::getId,
                RequestEntity::getLatitude,
                RequestEntity::getLongitude,
                r -> r.getCreationDate().truncatedTo(MINUTES)
        ).containsExactly(
                1L,
                parseDouble(latitude),
                parseDouble(longitude),
                LocalDateTime.now().truncatedTo(MINUTES)
        );
    }

    private void mockApiResponse() throws IOException {
        mockServerClient.when(request())
                .respond(HttpResponse.response()
                        .withStatusCode(OK.value())
                        .withContentType(APPLICATION_JSON)
                        .withBody(toJson(weatherArchiveApiResource())));
    }

    private String toJson(final WeatherArchiveApiResource weatherArchiveApiResource) throws JsonProcessingException {
        return objectMapper.writeValueAsString(weatherArchiveApiResource);
    }
}
