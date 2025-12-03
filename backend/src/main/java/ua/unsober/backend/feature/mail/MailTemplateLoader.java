package ua.unsober.backend.feature.mail;

import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import ua.unsober.backend.common.enums.MailTemplate;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@Component
public class MailTemplateLoader {

    public String getScheduleChangeText(String name, String course) {
        return loadTemplate(MailTemplate.SCHEDULE_CHANGE)
                .replace("{name}", name)
                .replace("{course}", course);
    }

    public String getStageChangeText(String name, String stage) {
        return loadTemplate(MailTemplate.STAGE_CHANGE)
                .replace("{name}", name)
                .replace("{stage}", stage);
    }

    @SneakyThrows
    private String loadTemplate(MailTemplate template) {
        ClassPathResource resource = new ClassPathResource(template.getPath());
        return Files.readString(resource.getFile().toPath(), StandardCharsets.UTF_8);
    }

}