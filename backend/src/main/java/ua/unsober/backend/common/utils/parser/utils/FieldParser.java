package ua.unsober.backend.common.utils.parser.utils;

import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import ua.unsober.backend.common.utils.parser.models.ClassSchedule;
import ua.unsober.backend.common.utils.parser.models.DaySchedule;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@NoArgsConstructor
public class FieldParser {

    public String parseSpecialty(Cell cell) {
        String regex = "\"([^\"]*)\"";
        String cellStringValue = cell.getStringCellValue();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cellStringValue);
        return matcher.find() ? matcher.group(1).trim().replace("`", "'") : "";
    }

    public int parseYearOfStudy(Cell cell) {
        String cellStringValue = cell.getStringCellValue();
        Pattern pattern = Pattern.compile("-?\\d+");
        Matcher matcher = pattern.matcher(cellStringValue);
        return matcher.find() ? Integer.parseInt(matcher.group()) : -1;
    }

    public String[] parseClassAndLecturer(Cell cell) {
        String cellStringValue = cell.getStringCellValue();
        String[] classAndLecturer = cellStringValue.split(",");
        classAndLecturer[0] = classAndLecturer[0].trim().replace("`", "'");
        classAndLecturer[1] = classAndLecturer[1].trim().replace("`", "'");
        return classAndLecturer;
    }

    public String parseClassGroup(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue()).trim();
            case BLANK -> "";
            default -> cell.toString().trim();
        };
    }

    public LocalTime[] parseTimeRange(Cell cell, DaySchedule currentDay) {
        if (hasOverlappingClasses(cell))
            return loadCurrentTimeRange(currentDay);
        String cellStringValue = cell.getStringCellValue();
        String[] times = cellStringValue.split("-");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
        LocalTime startTime = LocalTime.parse(times[0], formatter);
        LocalTime endTime = LocalTime.parse(times[1], formatter);
        return new LocalTime[]{startTime, endTime};
    }

    public List<Integer> parseWeeks(Cell cell) {
        String weeks = cell.getStringCellValue().trim();
        return parseWeekString(weeks);
    }

    public String parseDayName(Cell cell) {
        return cell.getStringCellValue().trim();
    }

    public String parseLocation(Cell cell) {
        return cell.getStringCellValue().trim();
    }

    private boolean hasOverlappingClasses(Cell cell) {
        String cellStringValue = cell.getStringCellValue();
        return cellStringValue.isEmpty();
    }

    private LocalTime[] loadCurrentTimeRange(DaySchedule currentDay) {
        ClassSchedule lastClass = currentDay.getClasses().getLast();
        LocalTime startTime = lastClass.getStartTime();
        LocalTime endTime = lastClass.getEndTime();
        return new LocalTime[]{startTime, endTime};
    }

    private List<Integer> parseWeekString(String weeks){
        if (isCommaSeparatedWeeks(weeks))
            return parseCommaSeparatedWeeks(weeks);
        if (isWeeksRange(weeks))
            return parseWeeksRange(weeks);
        if (isSingleWeek(weeks))
            return parseSingleWeek(weeks);
        return new ArrayList<>();
    }

    private boolean isWeeksRange(String weeks) {
        return weeks.contains("-");
    }

    private List<Integer> parseWeeksRange(String weeks) {
        String[] range = weeks.split("-");
        int start = Integer.parseInt(range[0].trim());
        int end = Integer.parseInt(range[1].trim());
        return IntStream.rangeClosed(start, end)
                .boxed()
                .toList();
    }

    private boolean isCommaSeparatedWeeks(String weeks) {
        return weeks.contains(",");
    }

    private List<Integer> parseCommaSeparatedWeeks(String weeks) {
        String[] weekArray = weeks.split(",");
        List<Integer> weekList = new ArrayList<>();
        for (String week : weekArray) {
            List<Integer> weekNums = parseWeekString(week.trim());
            weekList.addAll(weekNums);
        }
        return weekList;
    }

    private boolean isSingleWeek(String weeks) {
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        return pattern.matcher(weeks).matches();
    }

    private List<Integer> parseSingleWeek(String weeks) {
        int weekNumber = Integer.parseInt(weeks);
        List<Integer> weekList = new ArrayList<>();
        weekList.add(weekNumber);
        return weekList;
    }

}
