package pl.maciejbadziak.appweather.weatherarchive.adapter.out.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;
import pl.maciejbadziak.appweather.weatherarchive.adapter.out.rest.resources.HourlyReportApiResource;
import pl.maciejbadziak.appweather.weatherarchive.adapter.out.rest.resources.WeatherArchiveApiResource;
import pl.maciejbadziak.appweather.weatherarchive.domain.DailyReport;
import pl.maciejbadziak.appweather.weatherarchive.domain.WeatherArchive;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;
import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

@Component
public class WeatherArchiveApiResourceAssembler {

    public WeatherArchive assembleToWeatherArchive(final WeatherArchiveApiResource apiResource) {
        final List<DailyReport> dailyHistories = new ArrayList<>();

        final List<String> days = apiResource.getDaily().getTime();
        final Map<String, Double> avgDailyPrecipitations = getAvgDailyPrecipitations(apiResource.getHourly());

        days.forEach(day -> {
            DailyReport dailyReport = DailyReport.builder()
                    .date(assembleDate(day))
                    .sunrise(assembleSunrise(day, apiResource.getDaily().getSunrise()))
                    .sunset(assembleSunset(day, apiResource.getDaily().getSunset()))
                    .avgPrecipitation(avgDailyPrecipitations.get(day))
                    .build();
            dailyHistories.add(dailyReport);
        });

        return WeatherArchive.builder()
                .dailyReports(dailyHistories)
                .build();
    }

    private Map<String, Double> getAvgDailyPrecipitations(HourlyReportApiResource hourlyReport) {
        final List<String> keys = hourlyReport.getTime().stream()
                .map(date -> date.substring(0, 10))
                .toList();
        final List<Double> values = hourlyReport.getPrecipitation();

        final List<PrecipitationData> dailyPrecipitation = IntStream.range(0, keys.size()).boxed()
                .map(value -> new PrecipitationData(keys.get(value), values.get(value)))
                // Some API response data contains null values. It's not clear if it should be counted as 0.0 or ignored.
                .filter(x -> x.getValue() != null)
                .toList();

        final Map<String, Double> calculatedAvgPrecipitation = dailyPrecipitation.stream()
                .collect(Collectors.groupingBy(PrecipitationData::getDate, TreeMap::new, Collectors.averagingDouble(PrecipitationData::getValue)));

        return calculatedAvgPrecipitation.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entrySet -> round(entrySet.getValue())));
    }

    private LocalDate assembleDate(final String day) {
        return LocalDate.of(
                parseInt(day.substring(0, 4)),
                parseInt(day.substring(5, 7)),
                parseInt(day.substring(8)));
    }

    private static double round(double value) {
        BigDecimal bigDecimal = valueOf(value);
        bigDecimal = bigDecimal.setScale(2, HALF_UP);
        return bigDecimal.doubleValue();
    }

    private String assembleSunrise(final String day, final List<String> sunrises) {
        final String sunriseTime = sunrises.stream()
                .filter(sunriseDateTime -> sunriseDateTime.contains(day))
                .findFirst()
                .orElse(null);

        if (sunriseTime == null) {
            return null;
        }

        return sunriseTime.substring(11);
    }

    private String assembleSunset(final String day, final List<String> sunsets) {
        final String sunsetTime = sunsets.stream()
                .filter(sunsetDateTime -> sunsetDateTime.contains(day))
                .findFirst()
                .orElse(null);

        if (sunsetTime == null) {
            return null;
        }

        return sunsetTime.substring(11);
    }

    @Getter
    @AllArgsConstructor
    private static class PrecipitationData {
        private String date;
        private Double value;
    }
}
