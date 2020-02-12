package webservice.springboot2.test.web;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webservice.springboot2.test.service.posts.RecordsService;
import webservice.springboot2.test.web.dto.RecordsDto.RecordsListResponseDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class RecordsApiController {

    private final RecordsService recordsService;

    // 해당 날짜를 포함한 주의 기록을 가져온다
    @PostMapping("/api/v1/records")
    public @ResponseBody List<RecordsListResponseDto> getWeeklyRecords
            (@RequestBody Map<String, String> json) throws ParseException {
        System.out.println("---------getWeeklyRecords-------------------");
        String selectedDate = json.get("selectedDate");
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today = transFormat.parse(selectedDate);

        System.out.println("selectedDate="+today);
        List<RecordsListResponseDto> recordsListResponseDtos = recordsService.findByRecordDateBetween(today);

        return recordsListResponseDtos;
    }

}
