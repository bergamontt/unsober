package ua.unsober.backend.common.utils.crawler.saz;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import ua.unsober.backend.common.enums.EducationLevel;
import ua.unsober.backend.feature.subject.SubjectRequestDto;
import ua.unsober.backend.common.enums.Term;
import ua.unsober.backend.common.utils.crawler.EntityPageParser;

import java.math.BigDecimal;
import java.util.Objects;

public class SubjectPageParser implements EntityPageParser<SubjectRequestDto> {
    @Override
    public SubjectRequestDto extract(Document doc) {
        return SubjectRequestDto.builder()
                .name(getName(doc))
                .annotation(getAnnotation(doc))
                .facultyName(getFacultyName(doc))
                .departmentName(getDepartmentName(doc))
                .educationLevel(getEducationLevel(doc))
                .credits(getCredits(doc))
                .hoursPerWeek(getNumHoursPerWeek(doc))
                .term(getTerm(doc))
                .build();
    }

    private String getName(Document doc) {
        return Objects.requireNonNull(doc.selectFirst(".page-header h1")).ownText().trim().replaceAll("`", "'");
    }

    private String getAnnotation(Document doc) {
        Element annotationElement = doc.selectFirst("div.collapse.text-justify[id$='--info']");
        return annotationElement != null ? annotationElement.text().trim() : "";
    }

    private String getFacultyName(Document doc) {
        Element facultyElement = doc.selectFirst("td:matchesOwn(Факультет)");
        if (facultyElement != null) {
            return facultyElement.text();
        } else {
            return "";
        }
    }

    private String getDepartmentName(Document doc) {
        Element departmentElement = doc.selectFirst("td:matchesOwn(Кафедра)");
        if (departmentElement != null) {
            return departmentElement.text();
        } else {
            return "";
        }
    }

    private EducationLevel getEducationLevel(Document doc) {
        EducationLevel level = null;
        Element levelElement = doc.selectFirst("tr:has(th:matchesOwn(Освітній рівень)) td");
        if (levelElement != null) {
            String raw = levelElement.text().trim();
            switch (raw) {
                case "Бакалавр" -> level = EducationLevel.BATCHELOR;
                case "Магістр"  -> level = EducationLevel.MASTER;
            }
        }
        return level;
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

    private int getNumHoursPerWeek(Document doc) {
        Element hourElement = doc.selectFirst("td span:matchesOwn(год\\./тижд\\.)");
        int res = 0;
        if(hourElement != null) {
            String num = hourElement.text().split(" ")[0];
            res = Integer.parseInt(num);
        }
        return res;
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
