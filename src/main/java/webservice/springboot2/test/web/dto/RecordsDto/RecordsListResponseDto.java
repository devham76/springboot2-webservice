package webservice.springboot2.test.web.dto.RecordsDto;

import lombok.Getter;
import webservice.springboot2.test.domain.records.Records;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
public class RecordsListResponseDto implements Comparable<RecordsListResponseDto> {
    private Long id;
    private String content;
    private Date recordDate;
    private int hour;
    private int minute;

    public RecordsListResponseDto(Records entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.recordDate = entity.getRecordDate();
        this.hour = entity.getHour();
        this.minute = entity.getMinute();
    }

    @Override
    public int compareTo(RecordsListResponseDto another) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = simpleDateFormat.format(this.getRecordDate());
        String date2 = simpleDateFormat.format(another.getRecordDate());
        return date1.compareTo(date2);
    }
}
