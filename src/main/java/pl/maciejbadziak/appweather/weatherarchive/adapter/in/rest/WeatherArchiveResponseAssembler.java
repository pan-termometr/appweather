package pl.maciejbadziak.appweather.weatherarchive.adapter.in.rest;

import org.springframework.stereotype.Component;
import pl.maciejbadziak.appweather.weatherarchive.adapter.in.rest.resources.DailyReportResource;
import pl.maciejbadziak.appweather.weatherarchive.domain.DailyReport;
import pl.maciejbadziak.appweather.weatherarchive.domain.WeatherArchive;

import java.util.List;

@Component
public class WeatherArchiveResponseAssembler {

    public List<DailyReportResource> assemble(final WeatherArchive weatherArchive) {
        return weatherArchive.getDailyReports().stream()
                .map(this::assembleDailyReport)
                .toList();
    }

    private DailyReportResource assembleDailyReport(final DailyReport dailyReport) {
        return DailyReportResource.builder()
                .date(dailyReport.getDate())
                .sunrise(dailyReport.getSunrise())
                .sunset(dailyReport.getSunset())
                .avgPrecipitation(dailyReport.getAvgPrecipitation())
                .build();
    }
}
