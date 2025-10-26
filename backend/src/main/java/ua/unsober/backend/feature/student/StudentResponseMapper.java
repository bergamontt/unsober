package ua.unsober.backend.feature.student;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.feature.speciality.SpecialityResponseMapper;
import ua.unsober.backend.feature.user.User;

@Component
@RequiredArgsConstructor
public class StudentResponseMapper {
    private final SpecialityResponseMapper specialityResponseMapper;

    public StudentResponseDto toDto(Student student) {
        if (student == null)
            return null;
        StudentResponseDto response = StudentResponseDto.builder()
                .id(student.getId())
                .studyYear(student.getStudyYear())
                .specialty(specialityResponseMapper.toDto(student.getSpeciality()))
                .recordBookNumber(student.getRecordBookNumber())
                .build();
        if(student.getUser() != null){
            User user = student.getUser();
            response.setFirstName(user.getFirstName());
            response.setLastName(user.getLastName());
            response.setPatronymic(user.getPatronymic());
            response.setEmail(user.getEmail());
        }
        return response;
    }
}