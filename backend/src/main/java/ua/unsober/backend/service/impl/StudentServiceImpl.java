package ua.unsober.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.unsober.backend.dtos.request.StudentRequestDto;
import ua.unsober.backend.dtos.response.StudentResponseDto;
import ua.unsober.backend.entities.Student;
import ua.unsober.backend.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.backend.mapper.request.StudentRequestMapper;
import ua.unsober.backend.mapper.response.StudentResponseMapper;
import ua.unsober.backend.repository.StudentRepository;
import ua.unsober.backend.service.StudentService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentRequestMapper requestMapper;
    private final StudentResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    @Override
    public StudentResponseDto create(StudentRequestDto dto) {
        return responseMapper.toDto(
                studentRepository.save(requestMapper.toEntity(dto)));
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
}
