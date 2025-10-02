package ua.unsober.backend.common.utils.crawler;

import org.jsoup.nodes.Document;

public interface EntityPageParser<T> {
    T extract(Document doc);
}
