package work.iruby.course.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AuthControllerTests extends BaseIntegrationTest {
    @Test
    void status_register_login_status_logout_status() {
        HttpResponse<String> response = sendRequest("get", "/session", null, null);
        Assertions.assertEquals(401, response.statusCode());
        Assertions.assertTrue(response.body().contains("Unauthorized"));

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        String body = "username=iruby&password=234";
        response = sendRequest("post", "/user", headers, body);
        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertTrue(response.body().contains("iruby") && response.body().contains("user") && response.body().contains("username") && response.body().contains("id"));


        response = sendRequest("post", "/session", headers, body);
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertTrue(response.body().contains("iruby") && response.body().contains("user") && response.body().contains("username") && response.body().contains("id"));
        var cookie = "";
        Optional<String> optional = response.headers().firstValue("set-cookie");
        if (optional.isPresent()) {
            cookie = optional.get();
        } else {
            throw new RuntimeException("没有获取到cookie");
        }

        headers.clear();
        headers.put("Cookie", cookie);
        response = sendRequest("get", "/session", headers, null);
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertTrue(response.body().contains("iruby") && response.body().contains("user") && response.body().contains("username") && response.body().contains("id"));

        response = sendRequest("delete", "/session", headers, null);
        Assertions.assertEquals(204, response.statusCode());

        response = sendRequest("get", "/session", headers, null);
        Assertions.assertEquals(401, response.statusCode());
        Assertions.assertTrue(response.body().contains("Unauthorized"));

    }
}
