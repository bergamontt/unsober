package ua.unsober.backend.common.utils.crawler.saz;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;
import ua.unsober.backend.feature.subject.SubjectRequestDto;
import ua.unsober.backend.common.enums.Term;
import ua.unsober.backend.common.utils.crawler.EntityPageParser;

import java.math.BigDecimal;
import java.util.Objects;

@Component
public class SubjectPageParser implements EntityPageParser<SubjectRequestDto> {
    @Override
    public SubjectRequestDto extract(Document doc) {
        return new SubjectRequestDto(
                getName(doc),
                getAnnotation(doc),
                getCredits(doc),
                getTerm(doc)
        );
    }

    private String getName(Document doc) {
        return Objects.requireNonNull(doc.selectFirst(".page-header h1")).ownText().trim();
    }

    private String getAnnotation(Document doc) {
        Element annotationElement = doc.selectFirst("div.collapse.text-justify[id$='--info']");
        return annotationElement != null ? annotationElement.text().trim() : "";
    }

    private BigDecimal getCredits(Document doc) {
        Element creditElement = doc.selectFirst("td span:matchesOwn(кред\\.)");
        BigDecimal credits = BigDecimal.ZERO;
        if (creditElement != null) {
            String creditText = creditElement.text().split(" ")[0];
            credits = new BigDecimal(creditText);
        }
        return credits;
    }

    private Term getTerm(Document doc) {
        Term term = null;
        Element termElement = doc.selectFirst("tr:has(th:matchesOwn(Осінь|Весна|Літо)) th");
        if (termElement != null) {
            String raw = termElement.text().trim();
            switch (raw) {
                case "Осінь" -> term = Term.AUTUMN;
                case "Весна" -> term = Term.SPRING;
                case "Літо"  -> term = Term.SUMMER;
            }
        }
        return term;
    }
}
