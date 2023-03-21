package pl.maciejbadziak.appweather.weatherarchive.adapter.out.rest.webclient;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WeatherApiWebClientConfiguration {

    private final WeatherApiConfiguration config;

    @Bean("weather-archive")
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(config.getBaseUrl())
                .build();
    }
}


