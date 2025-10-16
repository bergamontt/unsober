package ua.unsober.backend.feature.student;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentRequestMapper requestMapper;
    private final StudentResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    private static final Marker STUDENT_ACTION = MarkerFactory.getMarker("STUDENT_ACTION");

    @Override
    public StudentResponseDto create(StudentRequestDto dto) {
        return responseMapper.toDto(
                studentRepository.save(requestMapper.toEntity(dto)));
    }

    @Override
    public List<StudentResponseDto> getAll() {
        logger.info(STUDENT_ACTION, "getAll");
        return studentRepository.findAll()
                .stream()
                .map(responseMapper::toDto)
                .toList();
    }

    @Override
    public StudentResponseDto getById(UUID id) {
        return responseMapper.toDto(
                studentRepository.findById(id).orElseThrow(() ->
                        notFound.get("error.student.notfound", id)));
    }

    @Override
    public StudentResponseDto update(UUID id, StudentRequestDto dto) {
        Student student = studentRepository.findById(id).orElseThrow(() ->
                notFound.get("error.student.notfound", id));
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
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        } else {
            throw notFound.get("error.student.notfound", id);
        }
    }

    @Override
    public StudentResponseDto getByEmail(String email) {
        return responseMapper.toDto(studentRepository.findByEmail(email)
                .orElseThrow(() -> notFound.get("error.student.notfound", email)));
    }
}
