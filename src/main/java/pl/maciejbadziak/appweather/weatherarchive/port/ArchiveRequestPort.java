package pl.maciejbadziak.appweather.weatherarchive.port;

public interface ArchiveRequestPort {

    void saveRequest(final double latitude, final double longitude);
}
