package work.iruby.course.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import work.iruby.course.common.AccountContext;
import work.iruby.course.common.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccountInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Constant.getCookie(request);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AccountContext.setCurrentAccount(null);
    }
}
