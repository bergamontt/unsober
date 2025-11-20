package ua.unsober.backend.common.aspects.allowedStage;

import ua.unsober.backend.common.enums.EnrollmentStage;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
public @interface AllowedAtStage {
    EnrollmentStage[] stages();
}
