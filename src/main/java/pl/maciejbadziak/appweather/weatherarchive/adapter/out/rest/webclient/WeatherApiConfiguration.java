package pl.maciejbadziak.appweather.weatherarchive.adapter.out.rest.webclient;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application.dependencies.weather-archive")
@RequiredArgsConstructor
@Getter
@Setter
public class WeatherApiConfiguration {

    private String baseUrl;
}
