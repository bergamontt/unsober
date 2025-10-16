package ua.unsober.backend.feature.request.enrollmentRequest;

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
public class EnrollmentRequestServiceImpl implements EnrollmentRequestService {

    private final EnrollmentRequestRepository enrollmentRequestRepository;
    private final EnrollmentRequestRequestMapper requestMapper;
    private final EnrollmentRequestResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    private static final Marker ENROLLMENT_ACTION = MarkerFactory.getMarker("ENROLLMENT_ACTION");
    private static final Marker ENROLLMENT_ERROR = MarkerFactory.getMarker("ENROLLMENT_ERROR");

    @Override
    public EnrollmentRequestResponseDto create(EnrollmentRequestRequestDto requestDto) {
        log.info(ENROLLMENT_ACTION, "Creating enrollment request...");
        EnrollmentRequest saved = enrollmentRequestRepository.save(requestMapper.toEntity(requestDto));
        log.info(ENROLLMENT_ACTION, "Enrollment request created with id={}", saved.getId());
        return responseMapper.toDto(saved);
    }

    @Override
    public List<EnrollmentRequestResponseDto> getAll() {
        log.info(ENROLLMENT_ACTION, "Fetching all enrollment requests...");
        List<EnrollmentRequestResponseDto> list = enrollmentRequestRepository.findAll()
                .stream()
                .map(responseMapper::toDto)
                .toList();
        log.info(ENROLLMENT_ACTION, "Fetched {} enrollment requests", list.size());
        return list;
    }

    @Override
    public EnrollmentRequestResponseDto getById(UUID id) {
        log.info(ENROLLMENT_ACTION, "Fetching enrollment request with id={}...", id);
        EnrollmentRequest enrollmentRequest = enrollmentRequestRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn(ENROLLMENT_ERROR, "Enrollment request with id={} not found", id);
                    return notFound.get("error.enrollment-request.notfound", id);
                });
        return responseMapper.toDto(enrollmentRequest);
    }

    @Override
    public EnrollmentRequestResponseDto update(UUID id, EnrollmentRequestRequestDto requestDto) {
        log.info(ENROLLMENT_ACTION, "Updating enrollment request with id={}...", id);
        EnrollmentRequest enrollmentRequest = enrollmentRequestRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn(ENROLLMENT_ERROR, "Attempt to update non-existing enrollment request with id={}", id);
                    return notFound.get("error.enrollment-request.notfound", id);
                });

        EnrollmentRequest newEnrollmentRequest = requestMapper.toEntity(requestDto);

        if (newEnrollmentRequest.getStudent() != null)
            enrollmentRequest.setStudent(newEnrollmentRequest.getStudent());
        if (newEnrollmentRequest.getCourse() != null)
            enrollmentRequest.setCourse(newEnrollmentRequest.getCourse());
        if (newEnrollmentRequest.getReason() != null)
            enrollmentRequest.setReason(newEnrollmentRequest.getReason());

        EnrollmentRequest updated = enrollmentRequestRepository.save(enrollmentRequest);
        log.info(ENROLLMENT_ACTION, "Enrollment request with id={} updated", updated.getId());
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        log.info(ENROLLMENT_ACTION, "Deleting enrollment request with id={}...", id);
        if (enrollmentRequestRepository.existsById(id)) {
            enrollmentRequestRepository.deleteById(id);
            log.info(ENROLLMENT_ACTION, "Deleted enrollment request with id={}", id);
        } else {
            log.warn(ENROLLMENT_ERROR, "Attempt to delete non-existing enrollment request with id={}", id);
            throw notFound.get("error.enrollment-request.notfound", id);
        }
    }
}


