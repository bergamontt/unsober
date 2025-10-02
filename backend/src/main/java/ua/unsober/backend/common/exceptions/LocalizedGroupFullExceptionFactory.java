package ua.unsober.backend.common.exceptions;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocalizedGroupFullExceptionFactory {
    private final MessageSource messageSource;

    public GroupFullException get() {
        String message = messageSource.getMessage(
                "error.group.full", null,
                LocaleContextHolder.getLocale());
        return new GroupFullException(message);
    }
}