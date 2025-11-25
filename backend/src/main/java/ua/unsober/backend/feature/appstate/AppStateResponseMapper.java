package ua.unsober.backend.feature.appstate;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppStateResponseMapper {
    AppStateResponseDto toDto(AppState appState);
}
