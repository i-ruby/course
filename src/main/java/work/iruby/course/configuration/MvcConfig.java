package work.iruby.course.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import work.iruby.course.interceptor.AccountInterceptor;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final AccountInterceptor accountInterceptor;

    public MvcConfig(AccountInterceptor accountInterceptor) {
        this.accountInterceptor = accountInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accountInterceptor);
    }
}
