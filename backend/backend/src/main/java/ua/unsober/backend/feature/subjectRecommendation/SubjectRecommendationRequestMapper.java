package ua.unsober.backend.feature.subjectRecommendation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.common.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.backend.feature.speciality.SpecialityRepository;
import ua.unsober.backend.feature.subject.SubjectRepository;

@Component
@RequiredArgsConstructor
public class SubjectRecommendationRequestMapper {
    private final SubjectRepository subjectRepository;
    private final SpecialityRepository specialityRepository;
    private final LocalizedEntityNotFoundExceptionFactory notFound;

    public SubjectRecommendation toEntity(SubjectRecommendationRequestDto dto) {
        if (dto == null)
            return null;
        SubjectRecommendation entity = SubjectRecommendation.builder()
                .recommendation(dto.getRecommendation())
                .build();

        java.util.UUID subjectId = dto.getSubjectId();
        java.util.UUID specialityId = dto.getSpecialityId();
        if (subjectId != null) {
            entity.setSubject(subjectRepository.findById(subjectId).orElseThrow(() ->
                    notFound.get("error.subject.notfound", subjectId)));
        }
        if (specialityId != null) {
            entity.setSpeciality(specialityRepository.findById(specialityId).orElseThrow(() ->
                    notFound.get("error.speciality.notfound", specialityId)));
        }
        return entity;
    }
}
