package pl.maciejbadziak.appweather.weatherarchive.adapter.out.rest.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@JsonInclude(NON_EMPTY)
@NoArgsConstructor
@AllArgsConstructor
public class HourlyReportApiResource {

    private List<String> time;
    private List<Double> precipitation;
}
