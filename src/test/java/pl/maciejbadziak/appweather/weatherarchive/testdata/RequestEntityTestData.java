package pl.maciejbadziak.appweather.weatherarchive.testdata;

import pl.maciejbadziak.appweather.weatherarchive.adapter.out.postgres.resources.RequestEntity;

import static java.time.LocalDateTime.now;

public class RequestEntityTestData {

    public static RequestEntity requestEntityWarsaw() {
        return RequestEntity.builder()
                .latitude(52.24)
                .longitude(21.02)
                .creationDate(now())
                .build();
    }
}
