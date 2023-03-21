package pl.maciejbadziak.appweather.weatherarchive.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.maciejbadziak.appweather.weatherarchive.adapter.in.rest.resources.DailyReportResource;
import pl.maciejbadziak.appweather.weatherarchive.exception.InvalidCoordinateException;
import pl.maciejbadziak.appweather.weatherarchive.usecase.GetWeatherArchiveByLocationUseCase;

import java.util.List;

import static pl.maciejbadziak.appweather.weatherarchive.service.CoordinatesValidatorService.validate;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class WeatherArchiveRestController {

    private final GetWeatherArchiveByLocationUseCase getWeatherArchiveByLocationUseCase;

    private final WeatherArchiveResponseAssembler weatherArchiveResponseAssembler;

    @GetMapping(
            path = "/weather-archive",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<DailyReportResource> getHistoricalWeatherByLocation(
            @RequestParam final double latitude,
            @RequestParam final double longitude
    ) throws InvalidCoordinateException {
            validate(latitude, longitude);
        return weatherArchiveResponseAssembler.assemble(getWeatherArchiveByLocationUseCase.get(latitude, longitude));
    }
}
