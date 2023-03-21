package pl.maciejbadziak.appweather.weatherarchive.adapter.out.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.maciejbadziak.appweather.weatherarchive.adapter.out.postgres.resources.RequestEntity;

@Repository
public interface ArchiveRequestRepository extends JpaRepository<RequestEntity, Long> {
}
