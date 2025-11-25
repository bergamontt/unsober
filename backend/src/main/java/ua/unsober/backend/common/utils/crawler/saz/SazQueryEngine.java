package ua.unsober.backend.common.utils.crawler.saz;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class SazQueryEngine {
    private final SazCookies cookies;
    public Document query(String uri, String path, Map<String, String> params) throws IOException {
        var connection = Jsoup.connect(uri + path)
                .cookies(cookies.getCookies())
                .header("X-PJAX", "true")
                .header("X-Requested-With", "XMLHttpRequest");
        if (params != null) {
            params.forEach(connection::data);
        }
        return connection.get();
    }
}
