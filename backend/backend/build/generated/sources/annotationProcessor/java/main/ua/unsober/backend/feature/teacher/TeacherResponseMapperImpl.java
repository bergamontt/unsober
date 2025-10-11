package ua.unsober.backend.feature.teacher;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-11T17:17:46+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class TeacherResponseMapperImpl implements TeacherResponseMapper {

    @Override
    public TeacherResponseDto toDto(Teacher teacher) {
        if ( teacher == null ) {
            return null;
        }

        TeacherResponseDto.TeacherResponseDtoBuilder teacherResponseDto = TeacherResponseDto.builder();

        teacherResponseDto.id( teacher.getId() );
        teacherResponseDto.firstName( teacher.getFirstName() );
        teacherResponseDto.lastName( teacher.getLastName() );
        teacherResponseDto.patronymic( teacher.getPatronymic() );
        teacherResponseDto.email( teacher.getEmail() );

        return teacherResponseDto.build();
    }
}
