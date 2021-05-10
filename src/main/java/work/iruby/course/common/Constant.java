package work.iruby.course.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class Constant {
    public final static String COOKIE_NAME = "COURSE_SESSION_ID";

    public static String getCookie(HttpServletRequest request) {
        return Arrays.stream(request.getCookies())
                .filter(co -> co.getName().equals(Constant.COOKIE_NAME))
                .map(Cookie::getValue)
                .findFirst().get();
    }
}
