package ua.unsober.backend.common.aspects.retry;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RetryAspect {
    @Around("@annotation(retryable)")
    public Object retry(ProceedingJoinPoint pjp, Retryable retryable) throws Throwable {
        int max = retryable.maxAttempts();
        long delay = retryable.delay();
        for (int attempt = 1; attempt <= max; attempt++) {
            try {
                return pjp.proceed();
            } catch (Throwable ex) {
                if (attempt == max) {
                    throw ex;
                }
                Thread.sleep(delay);
            }
        }
        return null;
    }
}