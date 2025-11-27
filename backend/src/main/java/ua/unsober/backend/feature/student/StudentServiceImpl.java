package ua.unsober.backend.feature.student;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.unsober.backend.common.enums.Role;
import ua.unsober.backend.common.enums.StudentStatus;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.backend.feature.user.User;
import ua.unsober.backend.feature.user.UserRepository;
import ua.unsober.backend.feature.user.UserRequestDto;
import ua.unsober.backend.feature.user.UserService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final StudentRequestMapper requestMapper;
    private final StudentResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    private static final String STUDENT_NOT_FOUND = "error.student.notfound";
    private static final String USER_NOT_FOUND = "error.user.notfound";

    @Scheduled(cron = "0 0 0 1 7 *")
    @Transactional
    public void updateStudentYears() {
        List<Student> students = studentRepository.findAllByStatus(StudentStatus.STUDYING);

        for (Student student : students) {
            if (student.getStudyYear() < 4) {
                student.setStudyYear(student.getStudyYear() + 1);
            } else {
                student.setStatus(StudentStatus.GRADUATED);
            }
        }
    }

    @Override
    public StudentResponseDto create(StudentRequestDto dto) {
        Student student = requestMapper.toEntity(dto);
        User user = userRepository.save(student.getUser());
        student.setUser(user);
        return responseMapper.toDto(studentRepository.save(student));
    }

    @Override
    public List<StudentResponseDto> getAll() {
        return studentRepository.findAll()
                .stream()
                .map(responseMapper::toDto)
                .toList();
    }

    @Override
    public StudentResponseDto getById(UUID id) {
        return responseMapper.toDto(
                studentRepository.findById(id).orElseThrow(() ->
                        notFound.get(STUDENT_NOT_FOUND, id)));
    }

    @Override
    public StudentResponseDto update(UUID id, StudentRequestDto dto) {
        Student student = studentRepository.findById(id).orElseThrow(() ->
                notFound.get(STUDENT_NOT_FOUND, id));

        Student newStudent = requestMapper.toEntity(dto);
        userService.update(student.getUser().getId(),
                UserRequestDto.builder()
                        .firstName(dto.getFirstName())
                        .lastName(dto.getLastName())
                        .patronymic(dto.getPatronymic())
                        .email(dto.getEmail())
                        .password(dto.getPassword())
                        .build()
        );
        if (newStudent.getRecordBookNumber() != null)
            student.setRecordBookNumber(newStudent.getRecordBookNumber());
        if (newStudent.getSpeciality() != null)
            student.setSpeciality(newStudent.getSpeciality());
        if (newStudent.getStudyYear() != null)
            student.setStudyYear(newStudent.getStudyYear());
        if (newStudent.getStatus() != null)
            student.setStatus(newStudent.getStatus());
        User updatedUser = userRepository.save(student.getUser());
        student.setUser(updatedUser);
        Student updated = studentRepository.save(student);
        return responseMapper.toDto(updated);
    }


    @Override
    public void delete(UUID id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        } else {
            throw notFound.get(STUDENT_NOT_FOUND, id);
        }
    }

    @Override
    public StudentResponseDto getByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                notFound.get(USER_NOT_FOUND, email)
        );
        if (user.getRole() == Role.STUDENT) {
            return responseMapper.toDto(studentRepository.findByUserId(user.getId())
                    .orElseThrow(() -> notFound.get(STUDENT_NOT_FOUND, email)));
        } else {
            throw notFound.get(STUDENT_NOT_FOUND, email);
        }
    }
}
