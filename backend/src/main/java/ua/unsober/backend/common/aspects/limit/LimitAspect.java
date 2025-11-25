package ua.unsober.backend.common.aspects.limit;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ua.unsober.backend.common.exceptions.LocalizedTooManyAttemptsExceptionFactory;
import ua.unsober.backend.common.exceptions.TooManyAttemptsException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
@RequiredArgsConstructor
public class LimitAspect {
    private final LocalizedTooManyAttemptsExceptionFactory tooManyAttempts;
    private final ConcurrentHashMap<String, List<Instant>> attempts = new ConcurrentHashMap<>();

    @Before("@annotation(limited)")
    public void before(Limited limited) throws TooManyAttemptsException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null)
            return;
        String username = authentication.getName();
        attempts.putIfAbsent(username, new ArrayList<>(List.of(Instant.now())));
        List<Instant> attemptTimes = attempts.get(username);
        Instant now = Instant.now();
        attemptTimes.removeIf(time -> time.plusSeconds(60).isBefore(now));
        if(attemptTimes.size() >= limited.perMinute()){
            throw tooManyAttempts.get();
        }
        attemptTimes.add(now);
    }
}
