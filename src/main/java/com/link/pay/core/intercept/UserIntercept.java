package com.link.pay.core.intercept;

import com.link.pay.core.annotation.WhiteApi;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Administrator
 */
@Slf4j
@Aspect
@Component
@AllArgsConstructor
public class UserIntercept {


    @Pointcut("execution(public * com.link.pay.controller..*.*(..))")
    public void preHandler() {

    }

    @Around("preHandler()")
    public Object postHandler(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        WhiteApi whiteApi = method.getAnnotation(WhiteApi.class);

        Object[] args = point.getArgs();
        if (args == null) {
            return point.proceed();
        }
        if (whiteApi != null) {
            return point.proceed(args);
        }
        return point.proceed(args);
    }

}
