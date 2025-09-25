package ua.unsober.backend.utils.crawler.saz;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ua.unsober.backend.utils.crawler.EntityPageFinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class SubjectPageFinder implements EntityPageFinder {
    private final SazQueryEngine sazQueryEngine;

    @Override
    public List<Document> findPages(Map<String, String> params) {
        List<Document> result = new ArrayList<>();
        try{
            List<Document> subjectListPages = findSubjectListPages(params);
            List<Integer> subjectIds = new ArrayList<>();
            for (Document page : subjectListPages) {
                subjectIds = Stream.concat(subjectIds.stream(),
                                getSubjectIdsFromPage(page).stream())
                        .toList();
            }
            for(Integer subjectId : subjectIds) {
                String path = String.format("/course/%d", subjectId);
                result.add(sazQueryEngine.query(path, null));
            }
        } catch (IOException ignored) {}
        return result;
    }

    private List<Document> findSubjectListPages(Map<String, String> params) throws IOException {
        String path = "/course/catalog";

        List<Document> result = new ArrayList<>();
        Document mainListPage = sazQueryEngine.query(path, params);
        int lastPage = getNumPagesFromPagination(mainListPage);
        for (int i = 1; i <= lastPage; i++) {
            Map<String, String> pageParams = new HashMap<>(params);
            pageParams.put("page", String.valueOf(i));
            result.add(sazQueryEngine.query(path, pageParams));
        }
        return result;
    }

    private int getNumPagesFromPagination(Document page){
        Element lastPageLink = page.selectFirst("li.last a");
        if (lastPageLink == null) {
            throw new RuntimeException("Page not found");
        }
        String href = lastPageLink.attr("href");
        String pageParam = href.replaceAll(".*page=(\\d+).*", "$1");
        return Integer.parseInt(pageParam);
    }

    private List<Integer> getSubjectIdsFromPage(Document page) {
        List<Integer> result = new ArrayList<>();
        Elements links = page.select("div.panel-footer > a.btn.btn-block.btn-default[href^=/course/]");
        for (Element link : links) {
            String href = link.attr("href");
            String code = href.replaceAll(".*/", "");
            result.add(Integer.parseInt(code));
        }
        return result;
    }
}
