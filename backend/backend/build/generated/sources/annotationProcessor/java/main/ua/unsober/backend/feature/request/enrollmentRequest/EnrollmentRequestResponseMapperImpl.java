package ua.unsober.backend.feature.request.enrollmentRequest;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ua.unsober.backend.feature.course.Course;
import ua.unsober.backend.feature.course.CourseResponseDto;
import ua.unsober.backend.feature.student.Student;
import ua.unsober.backend.feature.student.StudentResponseDto;
import ua.unsober.backend.feature.subject.Subject;
import ua.unsober.backend.feature.subject.SubjectResponseDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-11T17:17:46+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class EnrollmentRequestResponseMapperImpl implements EnrollmentRequestResponseMapper {

    @Override
    public EnrollmentRequestResponseDto toDto(EnrollmentRequest enrollmentRequest) {
        if ( enrollmentRequest == null ) {
            return null;
        }

        EnrollmentRequestResponseDto.EnrollmentRequestResponseDtoBuilder enrollmentRequestResponseDto = EnrollmentRequestResponseDto.builder();

        enrollmentRequestResponseDto.id( enrollmentRequest.getId() );
        enrollmentRequestResponseDto.student( studentToStudentResponseDto( enrollmentRequest.getStudent() ) );
        enrollmentRequestResponseDto.course( courseToCourseResponseDto( enrollmentRequest.getCourse() ) );
        enrollmentRequestResponseDto.reason( enrollmentRequest.getReason() );
        enrollmentRequestResponseDto.status( enrollmentRequest.getStatus() );
        enrollmentRequestResponseDto.createdAt( enrollmentRequest.getCreatedAt() );

        return enrollmentRequestResponseDto.build();
    }

    protected StudentResponseDto studentToStudentResponseDto(Student student) {
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

    protected CourseResponseDto courseToCourseResponseDto(Course course) {
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
}
