package pl.maciejbadziak.appweather.weatherarchive.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Builder
@Value
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WeatherArchive {

    List<DailyReport> dailyReports;
}
