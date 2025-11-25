package ua.unsober.backend.common.utils.crawler.saz;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ua.unsober.backend.common.enums.Recommendation;
import ua.unsober.backend.common.utils.crawler.EntityPageParser;

import java.util.HashMap;
import java.util.Map;

public class SubjectRecommendationPageParser implements EntityPageParser<Map<String, Recommendation>> {
    @Override
    public Map<String, Recommendation> extract(Document doc) {
        Map<String, Recommendation> recommendations = new HashMap<>();
        Element div = doc.selectFirst("div[id$='--spec'].collapse.text-justify");
        if (div == null)
            return recommendations;
        Elements rows = div.select("table tbody tr");
        for (Element row : rows) {
            Elements cells = row.select("td");
            if (cells.size() < 2)
                continue;
            String specName = cells.get(0).text().trim().replace("`", "'");
            String rawRecommendation = cells.get(1).text().trim();
            Recommendation rec = mapToEnum(rawRecommendation);
            if (!specName.isEmpty() && rec != null) {
                recommendations.put(specName, rec);
            }
        }
        return recommendations;
    }

    private Recommendation mapToEnum(String text) {
        return switch (text) {
            case "Обов`язкова" -> Recommendation.MANDATORY;
            case "Професійно-орієнтована" -> Recommendation.PROF_ORIENTED;
            default -> null;
        };
    }
}
