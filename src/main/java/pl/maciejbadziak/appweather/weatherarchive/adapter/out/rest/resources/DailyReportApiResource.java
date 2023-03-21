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
public class DailyReportApiResource {

    private List<String> time;
    private List<String> sunrise;
    private List<String> sunset;
}
