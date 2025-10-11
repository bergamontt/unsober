package ua.unsober.backend.common.utils.parser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ua.unsober.backend.common.utils.parser.config.ParserConfig;
import ua.unsober.backend.common.utils.parser.models.ClassSchedule;
import ua.unsober.backend.common.utils.parser.models.DaySchedule;
import ua.unsober.backend.common.utils.parser.models.Schedule;
import ua.unsober.backend.common.utils.parser.utils.FieldParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleParser {
    private ParserConfig config;
    private FieldParser fieldParser;
    private XSSFWorkbook workbook;
    private Schedule schedule;

    public Schedule parse(FileInputStream inputStream, ParserConfig config) throws IOException {
        loadWorkbook(inputStream, config);
        parseScheduleMetadata();
        parseWeekdays();
        return schedule;
    }

    public Schedule parse(FileInputStream inputStream) throws IOException {
        return parse(inputStream, new ParserConfig());
    }

    private void loadWorkbook(FileInputStream inputStream, ParserConfig config) throws IOException {
        this.workbook = new XSSFWorkbook(inputStream);
        this.fieldParser = new FieldParser();
        this.schedule = new Schedule();
        this.config = config;
    }

    private void parseScheduleMetadata() {
        XSSFSheet sheet = workbook.getSheetAt(config.getSheetNum());
        XSSFRow row = sheet.getRow(config.getMetadataRow());
        XSSFCell cell = row.getCell(config.getSpecialtyNameCell());
        schedule.setSpecialityName(fieldParser.parseSpecialty(cell));
        schedule.setYearOfStudy(fieldParser.parseYearOfStudy(cell));
    }

    private void parseWeekdays() {
        XSSFSheet sheet = workbook.getSheetAt(config.getSheetNum());
        List<DaySchedule> daySchedules = new ArrayList<>();
        DaySchedule currentDay = null;
        for (int rowNum = config.getStartingDayRow(); hasNextRow(sheet, rowNum); rowNum++) {
            XSSFRow row = sheet.getRow(rowNum);
            if (hasNoClass(row))
                continue;
            Cell weekdayCell = row.getCell(config.getWeekdayCell());
            if (isNewDay(weekdayCell)) {
                String dayName = fieldParser.parseDayName(weekdayCell);
                currentDay = new DaySchedule(dayName, new ArrayList<>());
                daySchedules.add(currentDay);
            }
            if (currentDay != null)
                currentDay.getClasses().add(parseClassSchedule(row, currentDay));
        }
        schedule.setDaySchedules(daySchedules);
    }

    private boolean hasNextRow(Sheet sheet, int rowNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null) return false;
        Cell timeCell = row.getCell(config.getTimeRangeCell());
        Cell lecturerCell = row.getCell(config.getClassAndLecturerCell());
        return timeCell != null || lecturerCell != null;
    }

    private boolean hasNoClass(Row row) {
        Cell classAndLecturerCell = row.getCell(config.getClassAndLecturerCell());
        return classAndLecturerCell.getStringCellValue().isEmpty();
    }

    private boolean isNewDay(Cell cell) {
        return cell != null && !cell.getStringCellValue().trim().isEmpty();
    }

    private ClassSchedule parseClassSchedule(Row row, DaySchedule currentDay) {
        ClassSchedule classSchedule = new ClassSchedule();
        parseTimeRange(classSchedule, row, currentDay);
        parseClassAndLecturer(classSchedule, row);
        parseClassGroup(classSchedule, row);
        parseWeeks(classSchedule, row);
        parseLocation(classSchedule, row);
        return classSchedule;
    }

    private void parseTimeRange(ClassSchedule classSchedule, Row row, DaySchedule currentDay) {
        Cell timeRangeCell = row.getCell(config.getTimeRangeCell());
        LocalTime[] timeRange = fieldParser.parseTimeRange(timeRangeCell, currentDay);
        classSchedule.setStartTime(timeRange[0]);
        classSchedule.setEndTime(timeRange[1]);
    }

    private void parseClassAndLecturer(ClassSchedule classSchedule, Row row) {
        Cell lecturerCell = row.getCell(config.getClassAndLecturerCell());
        String[] classAndLecturer = fieldParser.parseClassAndLecturer(lecturerCell);
        classSchedule.setClassName(classAndLecturer[0]);
        classSchedule.setLecturer(classAndLecturer[1]);
    }

    private void parseClassGroup(ClassSchedule classSchedule, Row row) {
        Cell groupCell = row.getCell(config.getGroupCell());
        classSchedule.setGroup(fieldParser.parseClassGroup(groupCell));
    }

    private void parseWeeks(ClassSchedule classSchedule, Row row) {
        Cell weeksCell = row.getCell(config.getWeeksCell());
        classSchedule.setWeeks(fieldParser.parseWeeks(weeksCell));
    }

    private void parseLocation(ClassSchedule classSchedule, Row row) {
        Cell locationCell = row.getCell(config.getLocationCell());
        classSchedule.setLocation(fieldParser.parseLocation(locationCell));
    }

}
