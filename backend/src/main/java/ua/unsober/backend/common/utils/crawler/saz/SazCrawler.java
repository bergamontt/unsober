package ua.unsober.backend.common.utils.crawler.saz;

import lombok.RequiredArgsConstructor;
import ua.unsober.backend.feature.subject.SubjectRequestDto;

import java.util.Collections;
import java.util.List;

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
