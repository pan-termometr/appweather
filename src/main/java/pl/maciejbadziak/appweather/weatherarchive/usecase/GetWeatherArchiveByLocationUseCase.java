package pl.maciejbadziak.appweather.weatherarchive.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.maciejbadziak.appweather.weatherarchive.domain.WeatherArchive;
import pl.maciejbadziak.appweather.weatherarchive.port.ArchiveRequestPort;
import pl.maciejbadziak.appweather.weatherarchive.port.GetWeatherArchiveByLocationPort;

@Component
@RequiredArgsConstructor
public class GetWeatherArchiveByLocationUseCase {

    final ArchiveRequestPort archiveRequestPort;
    final GetWeatherArchiveByLocationPort getWeatherArchiveByLocationPort;

    public WeatherArchive get(final double latitude, final double longitude) {
        archiveRequestPort.saveRequest(latitude, longitude);
        return getWeatherArchiveByLocationPort.getWeeklyReport(latitude, longitude);
    }
}
