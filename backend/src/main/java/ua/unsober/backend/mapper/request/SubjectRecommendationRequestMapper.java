package ua.unsober.backend.mapper.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.dtos.request.SubjectRecommendationRequestDto;
import ua.unsober.backend.entities.SubjectRecommendation;
import ua.unsober.backend.exceptions.LocalizedEntityNotFoundException;
import ua.unsober.backend.repository.SpecialityRepository;
import ua.unsober.backend.repository.SubjectRepository;

@Component
@RequiredArgsConstructor
public class SubjectRecommendationRequestMapper {
    private final SubjectRepository subjectRepository;
    private final SpecialityRepository specialityRepository;
    private final LocalizedEntityNotFoundException notFound;

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
                    notFound.forEntity("error.subject.notfound", subjectId)));
        }
        if (specialityId != null) {
            entity.setSpeciality(specialityRepository.findById(specialityId).orElseThrow(() ->
                    notFound.forEntity("error.speciality.notfound", specialityId)));
        }
        return entity;
    }
}
