package com.shop.base.aspect;

import java.util.Arrays;
import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 内部服务调用日志切面
 *
 */
@Aspect
@Order(6)
@Component
public class ClientLogAspect {

    private final static Logger LOGGER    = LoggerFactory.getLogger(ClientLogAspect.class);

    ThreadLocal<Long>           startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.shop.fegin.client..*.*(..))")
    public void clientLog() {
    }

    @Pointcut("execution(public * com.shop.fegin.client..*Hystrix.*(..))")
    //监控Hystrix结尾的
    public void clientMonitor() {
    }

    @Before("clientLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());

        // 记录下内部请求内容
        LOGGER.info("ClientAspect CLASS_METHOD : "
                    + joinPoint.getSignature().getDeclaringTypeName() + "."
                    + joinPoint.getSignature().getName());
        LOGGER.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

    }

    @AfterReturning(returning = "ret", pointcut = "clientLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完内部请求，返回内容
        if (Objects.nonNull(ret)) {
            if (ret.toString().length() > 256) {
                ret = ret.toString().substring(0, 256);
            }
            LOGGER.info("ClientAspect RESPONSE : " + ret);
        }
        LOGGER.info("ClientAspect SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
    }

    @AfterReturning(returning = "ret", pointcut = "clientMonitor()")
    public void clientMonitor(Object ret) throws Throwable {
        // 处理完内部请求，返回内容
        if (Objects.nonNull(ret)) {
            if (ret.toString().length() > 256) {
                ret = ret.toString().substring(0, 256);
            }
            LOGGER.info("ClientAspect RESPONSE : " + ret);
        }
        LOGGER.info("ClientAspect SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
    }

}
