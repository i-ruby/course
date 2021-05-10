package work.iruby.course.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class HttpCodeException extends RuntimeException {
    private int statusCode;
    private String message;

    public static HttpCodeException badRequest(String message) {
        return new HttpCodeException(HttpStatus.BAD_REQUEST.value(), message);
    }

    public static HttpCodeException unAuthorized(String message) {
        return new HttpCodeException(HttpStatus.UNAUTHORIZED.value(), message);
    }

    public static HttpCodeException forbidden(String message) {
        return new HttpCodeException(HttpStatus.FORBIDDEN.value(), message);
    }

    public static HttpCodeException notFound(String message) {
        return new HttpCodeException(HttpStatus.NOT_FOUND.value(), message);
    }

    public static HttpCodeException conflict(String message) {
        return new HttpCodeException(HttpStatus.CONFLICT.value(), message);
    }

    private HttpCodeException(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
