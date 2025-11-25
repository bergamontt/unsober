package ua.unsober.backend.common.aspects.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Component;

import org.springframework.data.domain.Pageable;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("within(ua.unsober.backend.feature..*)" +
            "&& @within(org.springframework.web.bind.annotation.RestController)" +
            "&& !within(ua.unsober.backend.feature.auth.*)")
    public void controllersPointcut() {}

    @Around("controllersPointcut()")
    public Object controllersLogger(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        String markerName = className.substring(className.lastIndexOf('.') + 1, className.length() - 10).toUpperCase();
        Marker action = MarkerFactory.getMarker( markerName + "_ACTION");
        Marker error = MarkerFactory.getMarker( markerName + "_ERROR");

        log.info(action, "Processing {}() request...", methodName);
        try {
            Object result = joinPoint.proceed();
            log.info(action, getLog(methodName, joinPoint.getArgs(), result, markerName));
            return result;
        } catch (Throwable ex) {
            log.warn(error, getBadLog(methodName, joinPoint.getArgs()));
            throw ex;
        }
    }

    private static String getLog(String methodName, Object[] args, Object res, String markerName) {
        return switch (methodName) {
            case "update", "delete", "getById" -> methodName + "() request for id=" + args[0] + " successful";
            case "getAll" -> "Fetched " + getSize(res) + " " + markerName.toLowerCase() + "s";
            default -> methodName + "() request successful";
        };
    }

    private static String getSize(Object res) {
        if (res instanceof java.util.List<?> list) {
            return String.valueOf(list.size());
        }
        if (res instanceof Pageable page) {
            return String.valueOf(page.getPageSize());
        }
        return "0";
    }

    private static String getBadLog(String methodName, Object[] args) {
        return switch (methodName) {
            case "update", "delete", "getById" -> methodName + "() request for id=" + args[0] + " failed";
            default -> methodName + "() request failed";
        };
    }
}
