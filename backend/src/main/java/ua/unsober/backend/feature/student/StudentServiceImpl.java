package ua.unsober.backend.feature.student;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentRequestMapper requestMapper;
    private final StudentResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    private static final Marker STUDENT_ACTION = MarkerFactory.getMarker("STUDENT_ACTION");

    @Override
    public StudentResponseDto create(StudentRequestDto dto) {
        log.info(STUDENT_ACTION, "Creating new student...");
        return responseMapper.toDto(
                studentRepository.save(requestMapper.toEntity(dto)));
    }

    @Override
    public List<StudentResponseDto> getAll() {
        log.info(STUDENT_ACTION, "Fetching all students...");
        return studentRepository.findAll()
                .stream()
                .map(responseMapper::toDto)
                .toList();
    }

    @Override
    public StudentResponseDto getById(UUID id) {
        log.info(STUDENT_ACTION, "Fetching student with id={}...", id);
        return responseMapper.toDto(
                studentRepository.findById(id).orElseThrow(() -> {
                    log.warn(STUDENT_ACTION, "Attempt to fetch a non-existent student with id={}", id);
                    return notFound.get("error.student.notfound", id);
                }));
    }

    @Override
    public StudentResponseDto update(UUID id, StudentRequestDto dto) {
        log.info(STUDENT_ACTION, "Updating student with id={}...", id);
        Student student = studentRepository.findById(id).orElseThrow(() -> {
            log.warn(STUDENT_ACTION, "Attempt to update non-existing student with id={}", id);
            return notFound.get("error.student.notfound", id);
        });

        Student newStudent = requestMapper.toEntity(dto);
        if (newStudent.getFirstName() != null)
            student.setFirstName(newStudent.getFirstName());
        if (newStudent.getLastName() != null)
            student.setLastName(newStudent.getLastName());
        if (newStudent.getPatronymic() != null)
            student.setPatronymic(newStudent.getPatronymic());
        if (newStudent.getRecordBookNumber() != null)
            student.setRecordBookNumber(newStudent.getRecordBookNumber());
        if (newStudent.getEmail() != null)
            student.setEmail(newStudent.getEmail());
        if (newStudent.getPasswordHash() != null)
            student.setPasswordHash(newStudent.getPasswordHash());
        if (newStudent.getSpeciality() != null)
            student.setSpeciality(newStudent.getSpeciality());
        if (newStudent.getStudyYear() != null)
            student.setStudyYear(newStudent.getStudyYear());
        Student updated = studentRepository.save(student);
        return responseMapper.toDto(updated);
    }


    @Override
    public void delete(UUID id) {
        log.info(STUDENT_ACTION, "Deleting student with id={}...", id);
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        } else {
            log.warn(STUDENT_ACTION, "Attempt to delete non-existing student with id={}", id);
            throw notFound.get("error.student.notfound", id);
        }
    }

    @Override
    public StudentResponseDto getByEmail(String email) {
        log.info(STUDENT_ACTION, "Fetching student by email...");
        return responseMapper.toDto(studentRepository.findByEmail(email)
                .orElseThrow(() -> {
                    log.warn(STUDENT_ACTION, "Attempt to fetch a student by non-existing email");
                    return notFound.get("error.student.notfound", email);
                }));
    }
}
