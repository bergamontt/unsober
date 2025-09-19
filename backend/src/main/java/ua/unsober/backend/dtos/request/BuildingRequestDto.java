package ua.unsober.backend.dtos.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingRequestDto {
    @NotBlank(message = "{building.name.required}")
    @Size(max = 100, message = "{building.name.size}")
    private String name;

    @NotBlank(message = "{building.address.required}")
    @Size(max = 200, message = "{building.address.size}")
    private String address;

    @NotNull(message = "{building.latitude.required}")
    @DecimalMin(value = "-90.0", message = "{building.latitude.min}")
    @DecimalMax(value = "90.0", message = "{building.latitude.max}")
    @Digits(integer = 2, fraction = 8, message = "{building.latitude.digits}")
    private BigDecimal latitude;

    @NotNull(message = "{building.longitude.required}")
    @DecimalMin(value = "-180.0", message = "{building.longitude.min}")
    @DecimalMax(value = "180.0", message = "{building.longitude.max}")
    @Digits(integer = 3, fraction = 8, message = "{building.longitude.digits}")
    private BigDecimal longitude;
}