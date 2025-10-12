package ua.unsober.backend.common.utils.crawler.saz;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class SazQueryEngine {
    private final SazCookies cookies;
    private static final String BASE_PATH = "https://my.ukma.edu.ua";

    public Document query(String path, Map<String, String> params) throws IOException {
        var connection = Jsoup.connect(BASE_PATH + path)
                .cookies(cookies.getCookies())
                .header("X-PJAX", "true")
                .header("X-Requested-With", "XMLHttpRequest");
        if (params != null) {
            params.forEach(connection::data);
        }
        return connection.get();
    }
}
