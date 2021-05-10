package work.iruby.course.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LoggerAspect {
    Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    @Pointcut("execution(public * *(..))")
    private void anyPublicOperation() {
    }

    @Pointcut("execution(* work.iruby.course.controller.*.*(..))")
    public void controllerOperation() {
    }

    @Around("anyPublicOperation() && controllerOperation()")
    public Object doBasicProfiling(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        assert requestAttributes != null;
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        assert request != null;
        logger.info(String.format("%s %s args:%s", request.getMethod(), request.getRequestURI(), Arrays.toString(pjp.getArgs())));
        Object retVal = pjp.proceed();
        logger.info("result:" + JSON.toJSONString(retVal));
        return retVal;
    }

}