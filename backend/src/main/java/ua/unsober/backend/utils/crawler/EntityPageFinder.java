package ua.unsober.backend.utils.crawler;

import org.jsoup.nodes.Document;

import java.util.List;
import java.util.Map;

public interface EntityPageFinder {
    List<Document> findPages(Map<String, String> params);
}
