package work.iruby.course.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import work.iruby.course.common.HttpCodeException;
import work.iruby.course.common.Message;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(HttpCodeException.class)
    public Message onHttpException(HttpServletResponse response, HttpCodeException httpCodeException) throws IOException {
        response.setStatus(httpCodeException.getStatusCode());
        return Message.of(httpCodeException.getMessage());
    }
}