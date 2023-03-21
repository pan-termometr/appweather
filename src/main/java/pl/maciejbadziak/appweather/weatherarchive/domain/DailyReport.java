package pl.maciejbadziak.appweather.weatherarchive.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DailyReport {

    LocalDate date;
    String sunrise;
    String sunset;
    Double avgPrecipitation;
}
