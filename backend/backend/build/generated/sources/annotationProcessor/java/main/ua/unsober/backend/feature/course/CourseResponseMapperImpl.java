package ua.unsober.backend.feature.course;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ua.unsober.backend.feature.subject.Subject;
import ua.unsober.backend.feature.subject.SubjectResponseDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-11T17:17:46+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class CourseResponseMapperImpl implements CourseResponseMapper {

    @Override
    public CourseResponseDto toDto(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseResponseDto.CourseResponseDtoBuilder courseResponseDto = CourseResponseDto.builder();

        courseResponseDto.id( course.getId() );
        courseResponseDto.subject( subjectToSubjectResponseDto( course.getSubject() ) );
        courseResponseDto.maxStudents( course.getMaxStudents() );
        courseResponseDto.numEnrolled( course.getNumEnrolled() );
        courseResponseDto.courseYear( course.getCourseYear() );

        return courseResponseDto.build();
    }

    protected SubjectResponseDto subjectToSubjectResponseDto(Subject subject) {
        if ( subject == null ) {
            return null;
        }

        SubjectResponseDto.SubjectResponseDtoBuilder subjectResponseDto = SubjectResponseDto.builder();

        subjectResponseDto.id( subject.getId() );
        subjectResponseDto.name( subject.getName() );
        subjectResponseDto.annotation( subject.getAnnotation() );
        subjectResponseDto.credits( subject.getCredits() );
        if ( subject.getTerm() != null ) {
            subjectResponseDto.term( subject.getTerm().name() );
        }

        return subjectResponseDto.build();
    }
}
