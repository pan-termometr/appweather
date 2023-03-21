package pl.maciejbadziak.appweather.weatherarchive.port;

import pl.maciejbadziak.appweather.weatherarchive.domain.WeatherArchive;

public interface GetWeatherArchiveByLocationPort {

    WeatherArchive getWeeklyReport(final double latitude, final double longitude);
}
