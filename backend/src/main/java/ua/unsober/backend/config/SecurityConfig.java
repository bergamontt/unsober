package ua.unsober.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.unsober.backend.common.enums.Role;
import ua.unsober.backend.common.filters.JwtFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/auth/**").permitAll();
                    auth.requestMatchers("/ui/**").permitAll();
                    auth.requestMatchers("/static/**").permitAll();
                    auth.requestMatchers("/api/swagger-ui/**", "/api/v3/api-docs/**").permitAll();
                    auth.requestMatchers(HttpMethod.GET,
                            "/api/subject/**", "/api/course/**", "/api/subject-recommendation/**",
                            "/api/enrollment-request/**", "/api/withdrawal-request/**",
                            "/api/student-enrollment/**"
                    ).hasRole(Role.STUDENT.name());
                    auth.requestMatchers(HttpMethod.POST,
                            "/api/enrollment-request/**", "/api/withdrawal-request/**",
                            "/api/student-enrollment/**"
                    ).hasRole(Role.STUDENT.name());
                    auth.requestMatchers("/api/**").hasRole(Role.ADMIN.name());
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}