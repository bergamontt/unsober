package ua.unsober.backend.common.utils.parser.models;

import lombok.Data;

import java.util.List;

@Data
public class Schedule {
    private String specialityName;
    private int yearOfStudy;
    private List<DaySchedule> daySchedules;
}
