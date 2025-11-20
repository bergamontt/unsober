package ua.unsober.backend.common.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocalizedEnrollmentActionNotAllowedExceptionFactory {
    private final MessageSource messageSource;

    public EnrollmentActionNotAllowedException get() {
        String message = messageSource.getMessage(
                "error.enrollment-action.not-allowed",
                null, LocaleContextHolder.getLocale());
        return new EnrollmentActionNotAllowedException(message);
    }
}
