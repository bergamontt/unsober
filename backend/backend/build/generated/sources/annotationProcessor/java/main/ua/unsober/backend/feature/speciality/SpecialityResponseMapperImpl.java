package ua.unsober.backend.feature.speciality;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import ua.unsober.backend.feature.department.Department;
import ua.unsober.backend.feature.department.DepartmentResponseDto;
import ua.unsober.backend.feature.faculty.Faculty;
import ua.unsober.backend.feature.faculty.FacultyResponseDto;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-11T17:17:46+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class SpecialityResponseMapperImpl implements SpecialityResponseMapper {

    @Override
    public SpecialityResponseDto toDto(Speciality speciality) {
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
}
