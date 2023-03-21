package pl.maciejbadziak.appweather;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerExtension;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static java.lang.String.format;

@ExtendWith(MockServerExtension.class)
public class MockServerIntegrationTest extends IntegrationTest {

    public static MockServerClient mockServerClient;

    public transient MockMvc mvc;

    @BeforeAll
    static void initMockServerClient(final MockServerClient initMockServerClient) {
        mockServerClient = initMockServerClient;
    }

    @BeforeEach
    void setUp(final WebApplicationContext webApplicationContext) {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void resetMockServer() {
        mockServerClient.reset();
    }

    @AfterAll
    static void stopMockServer() {
        mockServerClient.stop();
    }

    @DynamicPropertySource
    static void registerDynamicProperties(final DynamicPropertyRegistry registry) {
        registerApiWeather(registry);
    }

    private static void registerApiWeather(final DynamicPropertyRegistry registry) {
        final String apiWeatherUrl = format("http://localhost:%s/", mockServerClient.getPort());
        registry.add("application.dependencies.weather-archive.base-url", () -> apiWeatherUrl);
    }
}
