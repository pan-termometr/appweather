package pl.maciejbadziak.appweather.weatherarchive.adapter.in.rest;

import org.junit.jupiter.api.Test;
import pl.maciejbadziak.appweather.weatherarchive.adapter.in.rest.resources.DailyReportResource;
import pl.maciejbadziak.appweather.weatherarchive.domain.WeatherArchive;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static pl.maciejbadziak.appweather.weatherarchive.testdata.WeatherArchiveTestData.weatherArchive;

class WeatherArchiveResponseAssemblerTest {

    @Test
    void shouldAssembleWeatherArchiveResponse() {
        // given
        final WeatherArchive weatherArchive = weatherArchive();

        // when
        final List<DailyReportResource> result = new WeatherArchiveResponseAssembler().assemble(weatherArchive);

        // then
        assertThat(result).extracting(
                DailyReportResource::getDate,
                DailyReportResource::getSunrise,
                DailyReportResource::getSunset,
                DailyReportResource::getAvgPrecipitation
        ).containsExactly(
                tuple(
                        weatherArchive.getDailyReports().get(0).getDate(),
                        weatherArchive.getDailyReports().get(0).getSunrise(),
                        weatherArchive.getDailyReports().get(0).getSunset(),
                        weatherArchive.getDailyReports().get(0).getAvgPrecipitation()),
                tuple(
                        weatherArchive.getDailyReports().get(1).getDate(),
                        weatherArchive.getDailyReports().get(1).getSunrise(),
                        weatherArchive.getDailyReports().get(1).getSunset(),
                        weatherArchive.getDailyReports().get(1).getAvgPrecipitation()));
    }
}
