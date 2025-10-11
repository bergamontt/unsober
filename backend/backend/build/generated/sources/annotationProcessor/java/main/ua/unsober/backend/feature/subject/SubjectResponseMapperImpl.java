package ua.unsober.backend.feature.subject;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-11T17:17:47+0300",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.3.jar, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class SubjectResponseMapperImpl implements SubjectResponseMapper {

    @Override
    public SubjectResponseDto toDto(Subject subject) {
        if ( subject == null ) {
            return null;
        }

        SubjectResponseDto.SubjectResponseDtoBuilder subjectResponseDto = SubjectResponseDto.builder();

        subjectResponseDto.id( subject.getId() );
        subjectResponseDto.name( subject.getName() );
        subjectResponseDto.annotation( subject.getAnnotation() );
        subjectResponseDto.credits( subject.getCredits() );
        if ( subject.getTerm() != null ) {
            subjectResponseDto.term( subject.getTerm().name() );
        }

        return subjectResponseDto.build();
    }
}
