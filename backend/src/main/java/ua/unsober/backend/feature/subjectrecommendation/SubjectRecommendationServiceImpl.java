package ua.unsober.backend.feature.subjectrecommendation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public SubjectRecommendationResponseDto create(SubjectRecommendationRequestDto dto) {
        SubjectRecommendation saved = subjectRecommendationRepository.save(requestMapper.toEntity(dto));
        return responseMapper.toDto(saved);
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
        SubjectRecommendation recommendation = subjectRecommendationRepository.findById(id)
                .orElseThrow(() -> notFound.get("error.subject-recommendation.notfound", id));
        return responseMapper.toDto(recommendation);
    }

    @Override
    public SubjectRecommendationResponseDto getBySubjectAndSpecialityId(UUID subjectId, UUID specialityId) {
        SubjectRecommendation recommendation = subjectRecommendationRepository
                .findBySubjectIdAndSpecialityId(subjectId, specialityId)
                .orElseThrow(() -> notFound.get(
                        "error.subject-recommendation-by-ids.notfound", subjectId, specialityId));
        return responseMapper.toDto(recommendation);
    }

    @Override
    public boolean existsBySubjectAndSpecialityId(UUID subjectId, UUID specialityId) {
        return subjectRecommendationRepository.existsBySubjectIdAndSpecialityId(subjectId, specialityId);
    }

    @Override
    public SubjectRecommendationResponseDto update(UUID id, SubjectRecommendationRequestDto dto) {
        SubjectRecommendation recommendation = subjectRecommendationRepository.findById(id)
                .orElseThrow(() -> notFound.get("error.subject-recommendation.notfound", id));

        SubjectRecommendation newRecommendation = requestMapper.toEntity(dto);
        if (newRecommendation.getSubject() != null) recommendation.setSubject(newRecommendation.getSubject());
        if (newRecommendation.getSpeciality() != null) recommendation.setSpeciality(newRecommendation.getSpeciality());
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

