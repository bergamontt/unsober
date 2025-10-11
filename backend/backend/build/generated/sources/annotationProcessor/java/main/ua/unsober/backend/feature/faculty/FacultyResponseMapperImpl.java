package ua.unsober.backend.feature.faculty;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-11T17:17:46+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class FacultyResponseMapperImpl implements FacultyResponseMapper {

    @Override
    public FacultyResponseDto toDto(Faculty faculty) {
        if ( faculty == null ) {
            return null;
        }

        FacultyResponseDto.FacultyResponseDtoBuilder facultyResponseDto = FacultyResponseDto.builder();

        facultyResponseDto.id( faculty.getId() );
        facultyResponseDto.name( faculty.getName() );
        facultyResponseDto.description( faculty.getDescription() );

        return facultyResponseDto.build();
    }
}
