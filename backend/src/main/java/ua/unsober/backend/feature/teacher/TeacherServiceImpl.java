package ua.unsober.backend.feature.teacher;

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
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherRequestMapper requestMapper;
    private final TeacherResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    private static final Marker TEACHER_ACTION = MarkerFactory.getMarker("TEACHER_ACTION");
    private static final Marker TEACHER_ERROR = MarkerFactory.getMarker("TEACHER_ERROR");

    @Override
    public TeacherResponseDto create(TeacherRequestDto dto) {
        log.info(TEACHER_ACTION, "Creating new teacher...");
        Teacher saved = teacherRepository.save(requestMapper.toEntity(dto));
        log.info(TEACHER_ACTION, "Teacher created with id={}", saved.getId());
        return responseMapper.toDto(saved);
    }

    @Override
    public List<TeacherResponseDto> getAll() {
        log.info(TEACHER_ACTION, "Fetching all teachers...");
        List<TeacherResponseDto> result = teacherRepository.findAll()
                .stream()
                .map(responseMapper::toDto)
                .toList();
        log.info(TEACHER_ACTION, "Fetched {} teachers", result.size());
        return result;
    }

    @Override
    public TeacherResponseDto getById(UUID id) {
        log.info(TEACHER_ACTION, "Fetching teacher with id={}...", id);
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> {
            log.warn(TEACHER_ERROR, "Attempt to fetch non-existing teacher with id={}", id);
            return notFound.get("error.teacher.notfound", id);
        });
        return responseMapper.toDto(teacher);
    }

    @Override
    public TeacherResponseDto update(UUID id, TeacherRequestDto dto) {
        log.info(TEACHER_ACTION, "Updating teacher with id={}...", id);
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> {
            log.warn(TEACHER_ERROR, "Attempt to update non-existing teacher with id={}", id);
            return notFound.get("error.teacher.notfound", id);
        });

        Teacher newTeacher = requestMapper.toEntity(dto);
        if (newTeacher.getFirstName() != null) teacher.setFirstName(newTeacher.getFirstName());
        if (newTeacher.getLastName() != null) teacher.setLastName(newTeacher.getLastName());
        if (newTeacher.getPatronymic() != null) teacher.setPatronymic(newTeacher.getPatronymic());
        if (newTeacher.getEmail() != null) teacher.setEmail(newTeacher.getEmail());

        Teacher updated = teacherRepository.save(teacher);
        log.info(TEACHER_ACTION, "Successfully updated teacher with id={}", id);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        log.info(TEACHER_ACTION, "Deleting teacher with id={}...", id);
        if (teacherRepository.existsById(id)) {
            teacherRepository.deleteById(id);
            log.info(TEACHER_ACTION, "Teacher with id={} deleted", id);
        } else {
            log.warn(TEACHER_ERROR, "Attempt to delete non-existing teacher with id={}", id);
            throw notFound.get("error.teacher.notfound", id);
        }
    }
}
