package com.belentpatrus.gasstation.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class BusinessLoggingAspect {

    @Pointcut("execution(public * com.belentpatrus.gasstation.service..*(..))")
    void serviceLayer() {}

    @Around("serviceLayer()")
    public Object logCall(ProceedingJoinPoint pjp) throws Throwable {
        Logger log = LoggerFactory.getLogger(pjp.getTarget().getClass());
        String method = ((MethodSignature) pjp.getSignature()).toShortString(); // e.g. ProductService.updateProduct(..)

        long t0 = System.nanoTime();
        try {
            Object result = pjp.proceed();
            long ms = (System.nanoTime() - t0) / 1_000_000;
            log.atInfo()
                    .addKeyValue("layer", "service")
                    .addKeyValue("method", method)
                    .addKeyValue("duration_ms", ms)
                    .log("service_call");
            return result;
        } catch (Throwable ex) {
            long ms = (System.nanoTime() - t0) / 1_000_000;
            log.atError()
                    .addKeyValue("layer", "service")
                    .addKeyValue("method", method)
                    .addKeyValue("duration_ms", ms)
                    .setCause(ex)
                    .log("service_exception");
            throw ex;
        }
    }
}
