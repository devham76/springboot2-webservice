package webservice.springboot2.test.web.dto.RecordsDto;

import lombok.Builder;
import lombok.Getter;
import webservice.springboot2.test.domain.records.Records;

import java.util.Date;

@Getter
public class RecordsMarkDto {
    private String date;
    private String classname;

    public RecordsMarkDto(Records entity){
        date = entity.getRecordDate().toString().substring(0,10);
        classname = classnameByhour(entity.getHour());
    }
    // 공부한 시간별로 class이름을 다르게 설정한다
    public static String classnameByhour(int hour){
        if(hour > 0 && hour <= 4) return   "grade-2";
        else if(hour > 4 && hour <= 7) return "grade-3";
        else if(hour > 7 && hour <= 10) return "grade-4";
        else if(hour > 10) return "grade-5";
        return "grade-1";
    }

}
