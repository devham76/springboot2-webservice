package webservice.springboot2.test.web.dto.RecordsDto;

import lombok.Builder;
import lombok.Getter;
import webservice.springboot2.test.domain.posts.Posts;
import webservice.springboot2.test.domain.records.Records;

import java.util.Date;

@Getter
public class RecordsRequestDto {
    private Long id;
    private String content;
    private Date recordDate;
    private int hour;
    private int minute;

    @Builder
    public RecordsRequestDto(Records entity){
        this.id = entity.getId();
        this.content = entity.getContent();
        this.recordDate = entity.getRecordDate();
        this.hour = entity.getHour();
        this.minute = entity.getMinute();
    }
    public Records toEntity() {
        return Records.builder()
                .content(content)
                .recordDate(recordDate)
                .hour(hour)
                .minute(minute)
                .build();
    }
}
