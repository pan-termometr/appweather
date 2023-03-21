package pl.maciejbadziak.appweather.weatherarchive.testdata;

import pl.maciejbadziak.appweather.weatherarchive.domain.DailyReport;

import java.time.LocalDate;

public class DailyReportTestData {

    public static DailyReport dailyReportToday() {
        return DailyReport.builder()
                .date(LocalDate.now())
                .avgPrecipitation(1.3)
                .sunrise("10:10")
                .sunset("22:22")
                .build();
    }

    public static DailyReport dailyReportYesterday() {
        return DailyReport.builder()
                .date(LocalDate.now().minusDays(1))
                .avgPrecipitation(1.1)
                .sunrise("10:11")
                .sunset("22:21")
                .build();
    }
}
