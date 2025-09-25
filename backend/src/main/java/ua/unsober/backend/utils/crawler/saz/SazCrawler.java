package ua.unsober.backend.utils.crawler.saz;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.unsober.backend.dtos.request.SubjectRequestDto;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SazCrawler {
    private final SubjectPageFinder finder;
    private final SubjectPageParser parser;

    public List<SubjectRequestDto> getCSSubjects() {
        return finder.findPages(Collections.singletonMap("filter[faculty][0]", "123"))
                .stream()
                .map(parser::extract)
                .toList();
    }
}
