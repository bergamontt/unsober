package ua.unsober.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.unsober.backend.dtos.request.TeacherRequestDto;
import ua.unsober.backend.dtos.response.TeacherResponseDto;
import ua.unsober.backend.entities.Teacher;
import ua.unsober.backend.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.backend.mapper.request.TeacherRequestMapper;
import ua.unsober.backend.mapper.response.TeacherResponseMapper;
import ua.unsober.backend.repository.TeacherRepository;
import ua.unsober.backend.service.TeacherService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherRequestMapper requestMapper;
    private final TeacherResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    @Override
    public TeacherResponseDto create(TeacherRequestDto dto) {
        return responseMapper.toDto(
                teacherRepository.save(requestMapper.toEntity(dto)));
    }

    @Override
    public List<TeacherResponseDto> getAll() {
        return teacherRepository.findAll()
                .stream()
                .map(responseMapper::toDto)
                .toList();
    }

    @Override
    public TeacherResponseDto getById(UUID id) {
        return responseMapper.toDto(
                teacherRepository.findById(id).orElseThrow(
                        () -> notFound.get("error.teacher.notfound", id)
                )
        );
    }

    @Override
    public TeacherResponseDto update(UUID id, TeacherRequestDto dto) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() ->
                notFound.get("error.teacher.notfound", id));
        Teacher newTeacher = requestMapper.toEntity(dto);

        if (newTeacher.getFirstName() != null)
            teacher.setFirstName(newTeacher.getFirstName());
        if (newTeacher.getLastName() != null)
            teacher.setLastName(newTeacher.getLastName());
        if (newTeacher.getPatronymic() != null)
            teacher.setPatronymic(newTeacher.getPatronymic());
        if (newTeacher.getEmail() != null)
            teacher.setEmail(newTeacher.getEmail());

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
