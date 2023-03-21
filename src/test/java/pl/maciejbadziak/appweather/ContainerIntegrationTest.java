package pl.maciejbadziak.appweather;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ContainerIntegrationTest extends IntegrationTest {

    @Test
    void shouldRunPostgresContainer() {
        // then
        assertThat(POSTGRES_CONTAINER.isRunning()).isTrue();
    }
}
