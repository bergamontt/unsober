package ua.unsober.backend.mapper.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.dtos.request.SubjectRecommendationRequestDto;
import ua.unsober.backend.entities.SubjectRecommendation;
import ua.unsober.backend.exceptions.LocalizedEntityNotFoundExceptionFactory;
import ua.unsober.backend.repository.SpecialityRepository;
import ua.unsober.backend.repository.SubjectRepository;

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
