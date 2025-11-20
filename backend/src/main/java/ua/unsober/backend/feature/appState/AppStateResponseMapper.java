package ua.unsober.backend.feature.appState;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppStateResponseMapper {
    AppStateResponseDto toDto(AppState appState);
}
