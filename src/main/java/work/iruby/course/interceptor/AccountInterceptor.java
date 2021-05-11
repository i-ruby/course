package work.iruby.course.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import work.iruby.course.common.AccountContext;
import work.iruby.course.common.Constant;
import work.iruby.course.dao.AccountDao;
import work.iruby.course.dao.SessionDao;
import work.iruby.course.entity.Account;
import work.iruby.course.entity.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Component
public class AccountInterceptor implements HandlerInterceptor {
    private final SessionDao sessionDao;
    private final AccountDao accountDao;

    public AccountInterceptor(SessionDao sessionDao, AccountDao accountDao) {
        this.sessionDao = sessionDao;
        this.accountDao = accountDao;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<String> cookie = Constant.getCookie(request);
        cookie.ifPresent(co -> {
            Optional<Session> session = sessionDao.findOneByCookie(co);
            session.ifPresent(s -> {
                Optional<Account> account = accountDao.findById(s.getAccountId());
                account.ifPresent(AccountContext::setCurrentAccount);
            });
        });

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AccountContext.setCurrentAccount(null);
    }
}
