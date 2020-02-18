package webservice.springboot2.test.web.dto.RecordsDto;

import lombok.Builder;
import lombok.Getter;
import webservice.springboot2.test.domain.records.Records;

import java.util.Date;

@Getter
public class RecordsSaveRequestDto {
    private String content;
    private Date recordDate;
    private int hour;
    private int minute;

    @Builder
    public RecordsSaveRequestDto(String content, Date recordDate, int hour, int minute){
        this.content = content;
        this.recordDate = recordDate;
        this.hour = hour;
        this.minute = minute;
    }
    public Records toEntity(){
        return Records.builder()
                .content(content)
                .recordDate(recordDate)
                .hour(hour)
                .minute(minute)
                .build();
    }
}
