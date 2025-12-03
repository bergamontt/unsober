package ua.unsober.backend.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MailTemplate {
    SCHEDULE_CHANGE("templates/schedule-change.txt"),
    STAGE_CHANGE("templates/stage-change.txt");

    private final String path;
}