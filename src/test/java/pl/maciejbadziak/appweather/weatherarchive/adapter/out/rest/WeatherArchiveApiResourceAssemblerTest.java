package pl.maciejbadziak.appweather.weatherarchive.adapter.out.rest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.maciejbadziak.appweather.weatherarchive.adapter.out.rest.resources.WeatherArchiveApiResource;
import pl.maciejbadziak.appweather.weatherarchive.domain.DailyReport;
import pl.maciejbadziak.appweather.weatherarchive.domain.WeatherArchive;

import java.time.LocalDate;

import static org.assertj.core.groups.Tuple.tuple;
import static pl.maciejbadziak.appweather.weatherarchive.testdata.WeatherArchiveApiResourceTestData.weatherArchiveApiResource;

class WeatherArchiveApiResourceAssemblerTest {

    @Test
    void shouldAssemblerWeatherArchive() {
        // given
        final WeatherArchiveApiResource apiResource = weatherArchiveApiResource();

        // when
        final WeatherArchive result = new WeatherArchiveApiResourceAssembler().assembleToWeatherArchive(apiResource);

        // then
        Assertions.assertThat(result.getDailyReports()).extracting(
                DailyReport::getDate,
                DailyReport::getSunrise,
                DailyReport::getSunset,
                DailyReport::getAvgPrecipitation
        ).containsExactly(
                tuple(
                        LocalDate.of(2023, 3, 20),
                        "10:10",
                        "22:22",
                        1.4),
                tuple(
                        LocalDate.of(2023, 3, 19),
                        "10:11",
                        "22:21",
                        0.7)
        );
    }
}
