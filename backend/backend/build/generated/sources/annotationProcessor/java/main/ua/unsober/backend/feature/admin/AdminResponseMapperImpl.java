package ua.unsober.backend.feature.admin;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-11T17:17:46+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class AdminResponseMapperImpl implements AdminResponseMapper {

    @Override
    public AdminResponseDto toDto(Admin admin) {
        if ( admin == null ) {
            return null;
        }

        AdminResponseDto.AdminResponseDtoBuilder adminResponseDto = AdminResponseDto.builder();

        adminResponseDto.id( admin.getId() );
        adminResponseDto.email( admin.getEmail() );

        return adminResponseDto.build();
    }
}
