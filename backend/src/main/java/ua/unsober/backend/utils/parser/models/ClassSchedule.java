package ua.unsober.backend.utils.parser.models;

import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class ClassSchedule {
    private int weekday;
    private LocalTime startTime;
    private LocalTime endTime;
    private String className;
    private String lecturer;
    private String group;
    private List<Integer> weeks;
    private String location;
}
