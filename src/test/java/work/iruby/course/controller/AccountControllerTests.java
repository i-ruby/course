package work.iruby.course.controller;


import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import work.iruby.course.vo.RoleOnlyName;
import work.iruby.course.vo.UserWithRole;

import java.net.http.HttpResponse;
import java.util.Collections;

public class AccountControllerTests extends BaseIntegrationTest {

    @Test
    public void test() {
        HttpResponse<String> response = get("/user/1", STUDENT);
        Assertions.assertEquals(403, response.statusCode());
        response = get("/user/1", TEACHER);
        Assertions.assertEquals(403, response.statusCode());
        response = get("/user/1", ADMIN);
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertTrue(response.body().contains("学生"));
        response = get("/user/0", ADMIN);
        Assertions.assertEquals(404, response.statusCode());

        UserWithRole userWithRole = new UserWithRole();
        userWithRole.setId(1);
        userWithRole.setRoles(Collections.singletonList(new RoleOnlyName(null, "管理员")));
        response = patch("/user/1", JSON.toJSONString(userWithRole), TEACHER);
        Assertions.assertEquals(403, response.statusCode());
        response = patch("/user/1", JSON.toJSONString(userWithRole), ADMIN);
        Assertions.assertEquals(200, response.statusCode());

        response = get("/user/1", STUDENT);
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertTrue(response.body().contains("管理员"));

        response = get("/user?pageSize=10&pageNum=1&orderBy=id&orderType=asc&search=stu", TEACHER);
        Assertions.assertEquals(403, response.statusCode());
        response = get("/user?pageSize=10&pageNum=1&orderBy=id&orderType=asc&search=stu", ADMIN);
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertTrue(response.body().contains("\"pageNum\":1"));
        Assertions.assertTrue(response.body().contains("\"pageSize\":10"));
        Assertions.assertTrue(response.body().contains("\"username\":\"student1\""));

        response = get("/user?pageSize=10&pageNum=1&orderBy=id&orderType=asc", ADMIN);
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertTrue(response.body().contains("\"pageNum\":1"));
        Assertions.assertTrue(response.body().contains("\"pageSize\":10"));
        Assertions.assertTrue(response.body().contains("\"username\":\"student1\""));

    }
}
