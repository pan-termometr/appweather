package pl.maciejbadziak.appweather.weatherarchive.testdata;

import pl.maciejbadziak.appweather.weatherarchive.adapter.out.rest.resources.DailyReportApiResource;
import pl.maciejbadziak.appweather.weatherarchive.adapter.out.rest.resources.HourlyReportApiResource;
import pl.maciejbadziak.appweather.weatherarchive.adapter.out.rest.resources.WeatherArchiveApiResource;

import static java.util.List.of;

public class WeatherArchiveApiResourceTestData {

    public static WeatherArchiveApiResource weatherArchiveApiResource() {
        return WeatherArchiveApiResource.builder()
                .latitude("13.13")
                .longitude("-13.13")
                .daily(DailyReportApiResource.builder()
                        .time(of("2023-03-20", "2023-03-19"))
                        .sunrise(of("2023-03-20T10:10", "2023-03-19T10:11"))
                        .sunset(of("2023-03-20T22:22", "2023-03-19T22:21"))
                        .build())
                .hourly(HourlyReportApiResource.builder()
                        .time(of("2023-03-20T10:00", "2023-03-20T22:00", "2023-03-19T10:00", "2023-03-19T22:00"))
                        .precipitation(of(1.1, 1.7, 1.3, 0.1))
                        .build())
                .build();
    }
}
