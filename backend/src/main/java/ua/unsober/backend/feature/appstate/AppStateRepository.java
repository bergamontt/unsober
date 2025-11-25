package ua.unsober.backend.feature.appstate;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppStateRepository extends JpaRepository<AppState, Integer> {
    AppState findTopByOrderByIdAsc();
}
