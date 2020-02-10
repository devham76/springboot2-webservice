package webservice.springboot2.test.web.dto.RecordsDto;

import lombok.Getter;
import webservice.springboot2.test.domain.records.Records;

import java.util.Date;

@Getter
public class RecordsListResponseDto {
    private Long id;
    private String content;
    private Date recordDate;
    private int hour;
    private int minute;

    public RecordsListResponseDto(Records entity){
        this.id = entity.getId();
        this.content = entity.getContent();
        this.recordDate = entity.getRecordDate();
        this.hour = entity.getHour();
        this.minute = entity.getMinute();
    }
}
