package ua.unsober.backend.feature.subjectRecommendation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubjectRecommendationServiceImpl implements SubjectRecommendationService {

    private final SubjectRecommendationRepository subjectRecommendationRepository;
    private final SubjectRecommendationRequestMapper requestMapper;
    private final SubjectRecommendationResponseMapper responseMapper;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    @Override
    public SubjectRecommendationResponseDto create(SubjectRecommendationRequestDto dto) {
        return responseMapper.toDto(
                subjectRecommendationRepository.save(requestMapper.toEntity(dto)));
    }

    @Override
    public List<SubjectRecommendationResponseDto> getAll() {
        return subjectRecommendationRepository.findAll()
                .stream()
                .map(responseMapper::toDto)
                .toList();
    }

    @Override
    public SubjectRecommendationResponseDto getById(UUID id) {
        return responseMapper.toDto(
                subjectRecommendationRepository.findById(id).orElseThrow(
                        () -> notFound.get("error.subject-recommendation.notfound", id)
                )
        );
    }

    @Override
    public SubjectRecommendationResponseDto update(UUID id, SubjectRecommendationRequestDto dto) {
        SubjectRecommendation recommendation = subjectRecommendationRepository.findById(id).orElseThrow(() ->
                notFound.get("error.subject-recommendation.notfound", id));
        SubjectRecommendation newRecommendation = requestMapper.toEntity(dto);

        if (newRecommendation.getSubject() != null)
            recommendation.setSubject(newRecommendation.getSubject());
        if (newRecommendation.getSpeciality() != null)
            recommendation.setSpeciality(newRecommendation.getSpeciality());
        if (newRecommendation.getRecommendation() != null)
            recommendation.setRecommendation(newRecommendation.getRecommendation());

        SubjectRecommendation updated = subjectRecommendationRepository.save(recommendation);
        return responseMapper.toDto(updated);
    }

    @Override
    public void delete(UUID id) {
        if (subjectRecommendationRepository.existsById(id)) {
            subjectRecommendationRepository.deleteById(id);
        } else {
            throw notFound.get("error.subject-recommendation.notfound", id);
        }
    }

}
