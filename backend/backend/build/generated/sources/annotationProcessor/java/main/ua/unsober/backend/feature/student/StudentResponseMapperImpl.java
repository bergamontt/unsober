package ua.unsober.backend.feature.student;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-11T17:17:47+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class StudentResponseMapperImpl implements StudentResponseMapper {

    @Override
    public StudentResponseDto toDto(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentResponseDto.StudentResponseDtoBuilder studentResponseDto = StudentResponseDto.builder();

        studentResponseDto.id( student.getId() );
        studentResponseDto.firstName( student.getFirstName() );
        studentResponseDto.lastName( student.getLastName() );
        studentResponseDto.patronymic( student.getPatronymic() );
        studentResponseDto.recordBookNumber( student.getRecordBookNumber() );
        studentResponseDto.email( student.getEmail() );
        studentResponseDto.studyYear( student.getStudyYear() );

        return studentResponseDto.build();
    }
}
