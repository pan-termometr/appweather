package pl.maciejbadziak.appweather.weatherarchive.adapter.out.postgres;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.maciejbadziak.appweather.weatherarchive.adapter.out.postgres.resources.RequestEntity;

import static java.time.temporal.ChronoUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static pl.maciejbadziak.appweather.weatherarchive.testdata.RequestEntityTestData.requestEntityWarsaw;

@ExtendWith(MockitoExtension.class)
class ArchiveRequestPortAdapterTest {

    @Captor
    private transient ArgumentCaptor<RequestEntity> requestEntityCaptor;
    @Mock
    private transient ArchiveRequestRepository repositoryMock;
    @InjectMocks
    private transient ArchiveRequestPortAdapter adapter;

    @Test
    void shouldCallRepositoryToSave() {
        // given
        final RequestEntity requestEntity = requestEntityWarsaw();
        when(repositoryMock.save(requestEntityCaptor.capture())).thenReturn(requestEntity.withId(1L));

        // when
        adapter.saveRequest(requestEntityWarsaw().getLatitude(), requestEntityWarsaw().getLongitude());

        // then
        verify(repositoryMock, times(1)).save(requestEntityCaptor.getValue());
        assertThat(requestEntityCaptor.getValue()).extracting(
                RequestEntity::getId,
                RequestEntity::getLatitude,
                RequestEntity::getLongitude,
                r -> r.getCreationDate().truncatedTo(SECONDS)
        ).containsExactly(
                null,
                requestEntity.getLatitude(),
                requestEntity.getLongitude(),
                requestEntity.getCreationDate().truncatedTo(SECONDS)
        );
    }
}
