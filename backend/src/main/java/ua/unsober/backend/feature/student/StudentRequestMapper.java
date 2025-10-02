package ua.unsober.backend.feature.student;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.backend.feature.speciality.SpecialityRepository;

@Component
@RequiredArgsConstructor
public class StudentRequestMapper {
    private final SpecialityRepository specialityRepository;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    public Student toEntity(StudentRequestDto dto) {
        if (dto == null) return null;
        Student entity = Student.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .patronymic(dto.getPatronymic())
                .recordBookNumber(dto.getRecordBookNumber())
                .email(dto.getEmail())
                .passwordHash(dto.getPassword())
                .studyYear(dto.getStudyYear())
                .build();

        java.util.UUID specialityId = dto.getSpecialityId();
        if (specialityId != null) {
            entity.setSpeciality(specialityRepository.findById(specialityId).orElseThrow(() ->
                    notFound.get("error.speciality.notfound", specialityId)));
        }
        return entity;
    }
}
