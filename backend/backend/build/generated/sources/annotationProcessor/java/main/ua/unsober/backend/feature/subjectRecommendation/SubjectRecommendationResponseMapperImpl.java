package ua.unsober.backend.feature.subjectRecommendation;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ua.unsober.backend.feature.department.Department;
import ua.unsober.backend.feature.department.DepartmentResponseDto;
import ua.unsober.backend.feature.faculty.Faculty;
import ua.unsober.backend.feature.faculty.FacultyResponseDto;
import ua.unsober.backend.feature.speciality.Speciality;
import ua.unsober.backend.feature.speciality.SpecialityResponseDto;
import ua.unsober.backend.feature.subject.Subject;
import ua.unsober.backend.feature.subject.SubjectResponseDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-11T17:17:46+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class SubjectRecommendationResponseMapperImpl implements SubjectRecommendationResponseMapper {

    @Override
    public SubjectRecommendationResponseDto toDto(SubjectRecommendation recommendation) {
        if ( recommendation == null ) {
            return null;
        }

        SubjectRecommendationResponseDto.SubjectRecommendationResponseDtoBuilder subjectRecommendationResponseDto = SubjectRecommendationResponseDto.builder();

        subjectRecommendationResponseDto.id( recommendation.getId() );
        subjectRecommendationResponseDto.subject( subjectToSubjectResponseDto( recommendation.getSubject() ) );
        subjectRecommendationResponseDto.speciality( specialityToSpecialityResponseDto( recommendation.getSpeciality() ) );
        subjectRecommendationResponseDto.recommendation( recommendation.getRecommendation() );

        return subjectRecommendationResponseDto.build();
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

    protected FacultyResponseDto facultyToFacultyResponseDto(Faculty faculty) {
        if ( faculty == null ) {
            return null;
        }

        FacultyResponseDto.FacultyResponseDtoBuilder facultyResponseDto = FacultyResponseDto.builder();

        facultyResponseDto.id( faculty.getId() );
        facultyResponseDto.name( faculty.getName() );
        facultyResponseDto.description( faculty.getDescription() );

        return facultyResponseDto.build();
    }

    protected DepartmentResponseDto departmentToDepartmentResponseDto(Department department) {
        if ( department == null ) {
            return null;
        }

        DepartmentResponseDto.DepartmentResponseDtoBuilder departmentResponseDto = DepartmentResponseDto.builder();

        departmentResponseDto.id( department.getId() );
        departmentResponseDto.faculty( facultyToFacultyResponseDto( department.getFaculty() ) );
        departmentResponseDto.name( department.getName() );
        departmentResponseDto.description( department.getDescription() );

        return departmentResponseDto.build();
    }

    protected SpecialityResponseDto specialityToSpecialityResponseDto(Speciality speciality) {
        if ( speciality == null ) {
            return null;
        }

        SpecialityResponseDto.SpecialityResponseDtoBuilder specialityResponseDto = SpecialityResponseDto.builder();

        specialityResponseDto.id( speciality.getId() );
        specialityResponseDto.department( departmentToDepartmentResponseDto( speciality.getDepartment() ) );
        specialityResponseDto.name( speciality.getName() );
        specialityResponseDto.description( speciality.getDescription() );

        return specialityResponseDto.build();
    }
}
