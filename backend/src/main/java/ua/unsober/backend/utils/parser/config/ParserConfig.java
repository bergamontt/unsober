package ua.unsober.backend.utils.parser.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ParserConfig {
    private int sheetNum = 0;
    private int metadataRow = 6;
    private int specialtyNameCell = 0;
    private int startingDayRow = 10;
    private int weekdayCell = 0;
    private int timeRangeCell = 1;
    private int classAndLecturerCell = 2;
    private int groupCell = 3;
    private int weeksCell = 4;
    private int locationCell = 5;

    public static ParserConfig fromJsonString(String jsonConfig) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonConfig, ParserConfig.class);
    }

    public String toJsonString() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }
}
