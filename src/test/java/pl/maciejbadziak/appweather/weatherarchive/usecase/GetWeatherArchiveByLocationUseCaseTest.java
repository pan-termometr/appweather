package pl.maciejbadziak.appweather.weatherarchive.usecase;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.maciejbadziak.appweather.weatherarchive.domain.WeatherArchive;
import pl.maciejbadziak.appweather.weatherarchive.port.ArchiveRequestPort;
import pl.maciejbadziak.appweather.weatherarchive.port.GetWeatherArchiveByLocationPort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static pl.maciejbadziak.appweather.weatherarchive.testdata.WeatherArchiveTestData.weatherArchive;

@ExtendWith(MockitoExtension.class)
class GetWeatherArchiveByLocationUseCaseTest {

    @Mock
    private transient ArchiveRequestPort archiveRequestPortMock;
    @Mock
    private transient GetWeatherArchiveByLocationPort getWeatherArchiveByLocationPortMock;
    @InjectMocks
    private transient GetWeatherArchiveByLocationUseCase getWeatherArchiveByLocationUseCase;

    @Test
    void shouldGetWeatherArchive() {
        // given
        final double latitude = 13.13;
        final double longitude = -13.13;
        final WeatherArchive weatherArchive = weatherArchive();
        when(getWeatherArchiveByLocationPortMock.getWeeklyReport(latitude, longitude)).thenReturn(weatherArchive);

        // when
        final WeatherArchive result = getWeatherArchiveByLocationUseCase.get(latitude, longitude);

        // then
        verify(archiveRequestPortMock, times(1)).saveRequest(latitude, longitude);
        verify(getWeatherArchiveByLocationPortMock, times(1)).getWeeklyReport(latitude, longitude);
        assertThat(result).isEqualTo(weatherArchive);
    }
}
