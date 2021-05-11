package work.iruby.course.controller;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import work.iruby.course.CourseApplication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(classes = CourseApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class BaseIntegrationTest {

    private final static HttpClient client = HttpClient.newHttpClient();
    @LocalServerPort
    private int port;
    @Value("${spring.datasource.url}")
    String databaseUrl;
    @Value("${spring.datasource.username}")
    String databaseUsername;
    @Value("${spring.datasource.password}")
    String databasePassword;
    protected String url;

    @BeforeEach
    void setup() {
        url = String.format("http://localhost:%d/api/v1", port);
        Flyway flyway = Flyway.configure().dataSource(databaseUrl, databaseUsername, databasePassword).load();
        flyway.clean();
        flyway.migrate();
    }

    protected String loginAndGetCookie(String username, String password) throws IOException, InterruptedException {
        HttpResponse<String> response = sendRequest("get", "/session", null, null);
        System.out.println(response.body());
        return response.body();
    }

    protected String GetCookieAsStudent() throws IOException {
        return null;
    }


    protected HttpResponse<String> sendRequest(String method, String path, Map<String, String> headers, String body) {
        method = method.toUpperCase();
        var builder = HttpRequest.newBuilder();
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.forEach(builder::header);
        if (!headers.containsKey("Content-Type")) {
            builder.header("Content-Type", APPLICATION_JSON_VALUE);
        }
        if (!headers.containsKey("Accept")) {
            builder.header("Accept", APPLICATION_JSON_VALUE);
        }
        builder.uri(URI.create(url + path));
        if (body == null) {
            builder.method(method, HttpRequest.BodyPublishers.noBody());
        } else {
            builder.method(method, HttpRequest.BodyPublishers.ofString(body));
        }
        HttpRequest request = builder.build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
}