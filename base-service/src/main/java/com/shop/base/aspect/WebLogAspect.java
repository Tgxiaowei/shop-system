package com.shop.base.aspect;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Web层日志切面
 *
 */
@Aspect
@Order(5)
@Component
public class WebLogAspect {

    private static final Logger logger    = LoggerFactory.getLogger(WebLogAspect.class);

    ThreadLocal<Long>           startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.shop.*.controller..*.*(..))")
    public void webLog() {
    }

    @Pointcut("execution(public * com.shop.*.security.web.controller..*.*(..))")
    public void securityLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        doBeforeSomething(joinPoint);
    }

    @Before("securityLog()")
    public void doSecurityLogBefore(JoinPoint joinPoint) throws Throwable {
        doBeforeSomething(joinPoint);
    }

    private void doBeforeSomething(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "."
                    + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        doAfterSomeThing();
    }

    @AfterReturning(returning = "ret", pointcut = "securityLog()")
    public void doSecurityLogAfterReturning(Object ret) throws Throwable {
        doAfterSomeThing();
    }

    private void doAfterSomeThing() {
        // 处理完请求，返回内容
        //        LOGGER.info("RESPONSE : " + ret);
        logger.info("SPEND TIME : " + (System.currentTimeMillis() - startTime.get()) + "ms");
    }

}
