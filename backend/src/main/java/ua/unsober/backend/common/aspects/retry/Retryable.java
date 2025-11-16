package ua.unsober.backend.common.aspects.retry;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
public @interface Retryable {
    int maxAttempts() default 3;
    long delay() default 1000;
}