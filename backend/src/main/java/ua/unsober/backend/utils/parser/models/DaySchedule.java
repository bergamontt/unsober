package ua.unsober.backend.utils.parser.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DaySchedule {
    private String day;
    private List<ClassSchedule> classes;
}