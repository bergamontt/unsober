package ua.unsober.backend.feature.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtTokenService {
    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    public String generateToken(Authentication auth) {
        Instant now = Instant.now();
        List<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(auth.getName())
                .claim("roles", roles)
                .build();
        JwtEncoderParameters params = JwtEncoderParameters.from(
                JwsHeader.with(MacAlgorithm.HS256).build(), claims);
        return this.encoder.encode(params).getTokenValue();
    }

    public Long extractExpirationTime(String token) {
        Jwt jwt = decoder.decode(token);
        Instant exp = (Instant) jwt.getClaims().get("exp");
        return exp.toEpochMilli();
    }

}