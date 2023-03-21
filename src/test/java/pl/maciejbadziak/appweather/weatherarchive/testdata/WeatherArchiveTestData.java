package pl.maciejbadziak.appweather.weatherarchive.testdata;

import pl.maciejbadziak.appweather.weatherarchive.domain.WeatherArchive;

import java.util.List;

import static pl.maciejbadziak.appweather.weatherarchive.testdata.DailyReportTestData.dailyReportToday;
import static pl.maciejbadziak.appweather.weatherarchive.testdata.DailyReportTestData.dailyReportYesterday;

public class WeatherArchiveTestData {

    public static WeatherArchive weatherArchive() {
        return WeatherArchive.builder()
                .dailyReports(List.of(dailyReportToday(), dailyReportYesterday()))
                .build();
    }
}
