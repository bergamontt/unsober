package ua.unsober.backend.exceptions;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocalizedEntityNotFoundException {
    private final MessageSource messageSource;

    public EntityNotFoundException forEntity(String code, Object... args) {
        String message = messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
        return new EntityNotFoundException(message);
    }
}
