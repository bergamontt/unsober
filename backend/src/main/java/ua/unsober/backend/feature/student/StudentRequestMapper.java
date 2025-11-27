package ua.unsober.backend.feature.student;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ua.unsober.backend.common.enums.Role;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.backend.feature.speciality.SpecialityRepository;
import ua.unsober.backend.feature.user.User;

@Component
@RequiredArgsConstructor
public class StudentRequestMapper {
    private final SpecialityRepository specialityRepository;
    private final LocalizedEntityNotFoundExceptionFactory notFound;
    private final PasswordEncoder passwordEncoder;

    public Student toEntity(StudentRequestDto dto) {
        if (dto == null) return null;
        User user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .patronymic(dto.getPatronymic())
                .role(Role.STUDENT)
                .email(dto.getEmail())
                .passwordHash(dto.getPassword() != null ? passwordEncoder.encode(dto.getPassword()) : null)
                .build();
        Student entity = Student.builder()
                .user(user)
                .recordBookNumber(dto.getRecordBookNumber())
                .studyYear(dto.getStudyYear())
                .status(dto.getStatus())
                .build();

        java.util.UUID specialityId = dto.getSpecialityId();
        if (specialityId != null) {
            entity.setSpeciality(specialityRepository.findById(specialityId).orElseThrow(() ->
                    notFound.get("error.speciality.notfound", specialityId)));
        }
        return entity;
    }
}
