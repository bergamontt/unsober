package ua.unsober.backend.feature.subject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ua.unsober.backend.common.TestSecurityConfig;
import ua.unsober.backend.common.enums.Term;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SubjectController.class)
@Import(TestSecurityConfig.class)
public class SubjectControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private SubjectService subjectService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void createShouldReturnInsertedSubject() throws Exception {
        SubjectRequestDto request = SubjectRequestDto.builder()
                .name("Name")
                .credits(BigDecimal.TEN)
                .annotation("Annotation")
                .term(Term.AUTUMN)
                .build();
        SubjectResponseDto response = SubjectResponseDto.builder()
                .id(UUID.randomUUID())
                .name(request.getName())
                .credits(request.getCredits())
                .annotation(request.getAnnotation())
                .term(request.getTerm().toString())
                .build();
        when(subjectService.create(any())).thenReturn(response);
        mvc.perform(post("/subject")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(response.getName()))
                .andExpect(jsonPath("$.credits").value(response.getCredits()))
                .andExpect(jsonPath("$.annotation").value(response.getAnnotation()))
                .andExpect(jsonPath("$.term").value(response.getTerm()));
    }

    @Test
    void getAllShouldReturnAllExistingSubjects() throws Exception {
        UUID id = UUID.randomUUID();
        SubjectResponseDto subject = SubjectResponseDto.builder()
                .id(id)
                .name("Name")
                .credits(BigDecimal.TEN)
                .annotation("Annotation")
                .term(Term.AUTUMN.toString())
                .build();
        Page<SubjectResponseDto> page = new PageImpl<>(List.of(subject));
        when(subjectService.getAll(any(Pageable.class))).thenReturn(page);
        mvc.perform(get("/subject")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "name,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(id.toString()))
                .andExpect(jsonPath("$.content[0].name").value(subject.getName()))
                .andExpect(jsonPath("$.content[0].credits").value(subject.getCredits()))
                .andExpect(jsonPath("$.content[0].annotation").value(subject.getAnnotation()))
                .andExpect(jsonPath("$.content[0].term").value(subject.getTerm()));
    }

    @Test
    void getByIdShouldReturnSubjectWithGivenId() throws Exception {
        UUID id = UUID.randomUUID();
        SubjectResponseDto response = SubjectResponseDto.builder()
                .id(id)
                .build();
        when(subjectService.getById(id)).thenReturn(response);
        mvc.perform(get("/subject/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void updateShouldReturnModifiedSubject() throws Exception {
        UUID id = UUID.randomUUID();
        SubjectRequestDto request = SubjectRequestDto.builder()
                .name("New name")
                .credits(BigDecimal.TEN)
                .annotation("New annotation")
                .term(Term.AUTUMN)
                .build();
        SubjectResponseDto response = SubjectResponseDto.builder()
                .id(id)
                .name(request.getName())
                .credits(request.getCredits())
                .annotation(request.getAnnotation())
                .term(request.getTerm().toString())
                .build();
        when(subjectService.update(id, request)).thenReturn(response);
        mvc.perform(patch("/subject/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.getId().toString()))
                .andExpect(jsonPath("$.name").value(response.getName()))
                .andExpect(jsonPath("$.credits").value(response.getCredits()))
                .andExpect(jsonPath("$.annotation").value(response.getAnnotation()))
                .andExpect(jsonPath("$.term").value(response.getTerm()));
    }

    @Test
    void deleteShouldReturnOkStatus() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(subjectService).delete(id);
        mvc.perform(delete("/subject/{id}", id))
                .andExpect(status().isOk());
    }
}