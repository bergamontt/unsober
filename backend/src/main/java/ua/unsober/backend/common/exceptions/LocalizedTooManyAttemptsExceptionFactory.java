package ua.unsober.backend.common.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocalizedTooManyAttemptsExceptionFactory {
    private final MessageSource messageSource;

    public TooManyAttemptsException get() {
        String message = messageSource.getMessage(
                "error.too-many-attempts", null,
                LocaleContextHolder.getLocale());
        return new TooManyAttemptsException(message);
    }
}
