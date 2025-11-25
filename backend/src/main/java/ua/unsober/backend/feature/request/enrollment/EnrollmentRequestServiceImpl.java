package ua.unsober.backend.feature.request.enrollment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnrollmentRequestServiceImpl implements EnrollmentRequestService {

    private final EnrollmentRequestRepository enrollmentRequestRepository;
    private final EnrollmentRequestRequestMapper requestMapper;
    private final EnrollmentRequestResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    @Override
    public EnrollmentRequestResponseDto create(EnrollmentRequestRequestDto requestDto) {
        EnrollmentRequest saved = enrollmentRequestRepository.save(requestMapper.toEntity(requestDto));
        return responseMapper.toDto(saved);
    }

    @Override
    public List<EnrollmentRequestResponseDto> getAll() {
        return enrollmentRequestRepository.findAll()
                .stream()
                .map(responseMapper::toDto)
                .toList();
    }

    @Override
    public EnrollmentRequestResponseDto getById(UUID id) {
        EnrollmentRequest enrollmentRequest = enrollmentRequestRepository.findById(id)
                .orElseThrow(() -> notFound.get("error.enrollment-request.notfound", id));
        return responseMapper.toDto(enrollmentRequest);
    }

    @Override
    public EnrollmentRequestResponseDto update(UUID id, EnrollmentRequestRequestDto requestDto) {
        EnrollmentRequest enrollmentRequest = enrollmentRequestRepository.findById(id)
                .orElseThrow(() -> notFound.get("error.enrollment-request.notfound", id));

        EnrollmentRequest newEnrollmentRequest = requestMapper.toEntity(requestDto);

        if (newEnrollmentRequest.getStudent() != null)
            enrollmentRequest.setStudent(newEnrollmentRequest.getStudent());
        if (newEnrollmentRequest.getCourse() != null)
            enrollmentRequest.setCourse(newEnrollmentRequest.getCourse());
        if (newEnrollmentRequest.getReason() != null)
            enrollmentRequest.setReason(newEnrollmentRequest.getReason());

        EnrollmentRequest updated = enrollmentRequestRepository.save(enrollmentRequest);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        if (enrollmentRequestRepository.existsById(id)) {
            enrollmentRequestRepository.deleteById(id);
        } else {
            throw notFound.get("error.enrollment-request.notfound", id);
        }
    }
}


