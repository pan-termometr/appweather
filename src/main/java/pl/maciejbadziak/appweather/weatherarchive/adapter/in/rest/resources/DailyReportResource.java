package pl.maciejbadziak.appweather.weatherarchive.adapter.in.rest.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@JsonInclude(NON_EMPTY)
public class DailyReportResource {

    @NotNull
    private final LocalDate date;
    @NotNull
    private final String sunrise;
    @NotNull
    private final String sunset;
    private final Double avgPrecipitation;
}
