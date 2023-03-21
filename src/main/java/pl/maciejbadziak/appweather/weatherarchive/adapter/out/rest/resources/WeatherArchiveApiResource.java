package pl.maciejbadziak.appweather.weatherarchive.adapter.out.rest.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@JsonInclude(NON_EMPTY)
@NoArgsConstructor
@AllArgsConstructor
public class WeatherArchiveApiResource {

    private String latitude;
    private String longitude;
    private HourlyReportApiResource hourly;
    private DailyReportApiResource daily;
}
