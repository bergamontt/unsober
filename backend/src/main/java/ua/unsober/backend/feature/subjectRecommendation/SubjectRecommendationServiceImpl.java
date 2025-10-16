package ua.unsober.backend.feature.subjectRecommendation;

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
public class SubjectRecommendationServiceImpl implements SubjectRecommendationService {

    private final SubjectRecommendationRepository subjectRecommendationRepository;
    private final SubjectRecommendationRequestMapper requestMapper;
    private final SubjectRecommendationResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    private static final Marker RECOMMENDATION_ACTION = MarkerFactory.getMarker("RECOMMENDATION_ACTION");

    @Override
    public SubjectRecommendationResponseDto create(SubjectRecommendationRequestDto dto) {
        log.info(RECOMMENDATION_ACTION, "Creating new subject recommendation...");
        SubjectRecommendation saved = subjectRecommendationRepository.save(requestMapper.toEntity(dto));
        log.info(RECOMMENDATION_ACTION, "Subject recommendation created with id={}", saved.getId());
        return responseMapper.toDto(saved);
    }

    @Override
    public List<SubjectRecommendationResponseDto> getAll() {
        log.info(RECOMMENDATION_ACTION, "Fetching all subject recommendations...");
        List<SubjectRecommendationResponseDto> result = subjectRecommendationRepository.findAll()
                .stream()
                .map(responseMapper::toDto)
                .toList();
        log.info(RECOMMENDATION_ACTION, "Fetched {} subject recommendations", result.size());
        return result;
    }

    @Override
    public SubjectRecommendationResponseDto getById(UUID id) {
        log.info(RECOMMENDATION_ACTION, "Fetching subject recommendation with id={}...", id);
        SubjectRecommendation recommendation = subjectRecommendationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn(RECOMMENDATION_ACTION, "Attempt to fetch non-existing subject recommendation with id={}", id);
                    return notFound.get("error.subject-recommendation.notfound", id);
                });
        return responseMapper.toDto(recommendation);
    }

    @Override
    public SubjectRecommendationResponseDto update(UUID id, SubjectRecommendationRequestDto dto) {
        log.info(RECOMMENDATION_ACTION, "Updating subject recommendation with id={}...", id);
        SubjectRecommendation recommendation = subjectRecommendationRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn(RECOMMENDATION_ACTION, "Attempt to update non-existing subject recommendation with id={}", id);
                    return notFound.get("error.subject-recommendation.notfound", id);
                });

        SubjectRecommendation newRecommendation = requestMapper.toEntity(dto);
        if (newRecommendation.getSubject() != null) recommendation.setSubject(newRecommendation.getSubject());
        if (newRecommendation.getSpeciality() != null) recommendation.setSpeciality(newRecommendation.getSpeciality());
        if (newRecommendation.getRecommendation() != null)
            recommendation.setRecommendation(newRecommendation.getRecommendation());

        SubjectRecommendation updated = subjectRecommendationRepository.save(recommendation);
        log.info(RECOMMENDATION_ACTION, "Successfully updated subject recommendation with id={}", id);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        log.info(RECOMMENDATION_ACTION, "Deleting subject recommendation with id={}...", id);
        if (subjectRecommendationRepository.existsById(id)) {
            subjectRecommendationRepository.deleteById(id);
            log.info(RECOMMENDATION_ACTION, "Subject recommendation with id={} deleted", id);
        } else {
            log.warn(RECOMMENDATION_ACTION, "Attempt to delete non-existing subject recommendation with id={}", id);
            throw notFound.get("error.subject-recommendation.notfound", id);
        }
    }
}

