package ua.unsober.backend.common.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocalizedCourseFullExceptionFactory {
    private final MessageSource messageSource;

    public CourseFullException get() {
        String message = messageSource.getMessage(
                "error.course.full", null,
                LocaleContextHolder.getLocale());
        return new CourseFullException(message);
    }
}
