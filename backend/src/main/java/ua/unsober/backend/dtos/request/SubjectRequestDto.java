package ua.unsober.backend.dtos.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectRequestDto {
    @NotBlank(message = "{subject.name.required}")
    @Size(max = 100, message = "{subject.name.size}")
    private String name;

    @Size(max = 5000, message = "{subject.annotation.size}")
    private String annotation;

    @NotNull(message = "{subject.credits.required}")
    @Digits(integer = 2, fraction = 1, message = "{subject.credits.digits}")
    private BigDecimal credits;

    @NotNull(message = "{subject.term.required}")
    @Size(max = 10, message = "{subject.term.size}")
    private String term;
}