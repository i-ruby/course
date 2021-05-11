package work.iruby.course.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.stream.Stream;

public class Constant {
    public final static String COOKIE_NAME = "COURSE_SESSION_ID";

    public static Optional<String> getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return Optional.empty();
        }
        return Stream.of(cookies).filter(co -> co.getName().equals(Constant.COOKIE_NAME))
                .map(Cookie::getValue)
                .findFirst();
    }
}
