package ua.unsober.backend.feature.teacher;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public TeacherResponseDto create(TeacherRequestDto dto) {
        Teacher saved = teacherRepository.save(requestMapper.toEntity(dto));
        return responseMapper.toDto(saved);
    }

    @Override
    public List<TeacherResponseDto> getAll() {
        List<TeacherResponseDto> result = teacherRepository.findAll()
                .stream()
                .map(responseMapper::toDto)
                .toList();
        return result;
    }

    @Override
    public TeacherResponseDto getById(UUID id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> {
            return notFound.get("error.teacher.notfound", id);
        });
        return responseMapper.toDto(teacher);
    }

    @Override
    public TeacherResponseDto update(UUID id, TeacherRequestDto dto) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> {
            return notFound.get("error.teacher.notfound", id);
        });

        Teacher newTeacher = requestMapper.toEntity(dto);
        if (newTeacher.getFirstName() != null) teacher.setFirstName(newTeacher.getFirstName());
        if (newTeacher.getLastName() != null) teacher.setLastName(newTeacher.getLastName());
        if (newTeacher.getPatronymic() != null) teacher.setPatronymic(newTeacher.getPatronymic());
        if (newTeacher.getEmail() != null) teacher.setEmail(newTeacher.getEmail());

        Teacher updated = teacherRepository.save(teacher);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        if (teacherRepository.existsById(id)) {
            teacherRepository.deleteById(id);
        } else {
            throw notFound.get("error.teacher.notfound", id);
        }
    }
}
