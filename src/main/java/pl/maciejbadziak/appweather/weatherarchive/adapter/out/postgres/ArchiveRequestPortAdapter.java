package pl.maciejbadziak.appweather.weatherarchive.adapter.out.postgres;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.maciejbadziak.appweather.weatherarchive.adapter.out.postgres.resources.RequestEntity;
import pl.maciejbadziak.appweather.weatherarchive.port.ArchiveRequestPort;

import static java.time.LocalDateTime.now;

@Component
@RequiredArgsConstructor
public class ArchiveRequestPortAdapter implements ArchiveRequestPort {

    private static final String SUCCESS_MESSAGE = "Request successfully saved in db with id: {}";
    private static final Logger LOG = LoggerFactory.getLogger(ArchiveRequestPortAdapter.class);
    private final ArchiveRequestRepository repository;

    @Override
    public void saveRequest(final double latitude, final double longitude) {
        final RequestEntity entityRequest = RequestEntity.builder()
                .creationDate(now())
                .latitude(latitude)
                .longitude(longitude)
                .build();

        final RequestEntity savedRequest = repository.save(entityRequest);

        if (savedRequest.getId() != null) {
            LOG.info(SUCCESS_MESSAGE, savedRequest.getId());
        }
    }
}
