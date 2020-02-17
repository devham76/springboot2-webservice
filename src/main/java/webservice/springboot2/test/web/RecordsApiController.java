package webservice.springboot2.test.web;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webservice.springboot2.test.service.posts.RecordsService;
import webservice.springboot2.test.web.dto.PostsUpdateRequestDto;
import webservice.springboot2.test.web.dto.RecordsDto.RecordsListResponseDto;
import webservice.springboot2.test.web.dto.RecordsDto.RecordsRequestDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class RecordsApiController {

    private final RecordsService recordsService;

    // 해당 날짜를 포함한 week(주)의 기록을 가져온다
    @PostMapping("/api/v1/records")
    public @ResponseBody List<RecordsListResponseDto> getWeeklyRecords
            (@RequestBody Map<String, String> json) throws ParseException {
        String selectedDate = json.get("selectedDate");
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today = transFormat.parse(selectedDate);

        List<RecordsListResponseDto> recordsListResponseDtos = recordsService.findByRecordDateBetween(today);
        return recordsListResponseDtos;
    }
    // 글 수정 또는 저장
    @PostMapping("/api/v1/records/{id}")
    public Long saveOrUpdateRecords(@PathVariable Long id, @RequestBody RecordsRequestDto requestDto){
        System.out.println("saveOrUpdateRecords----id="+id);
        if(id == -1)
            return recordsService.save(requestDto);
        else
            return recordsService.update(id, requestDto);

    }
    // 해당 날짜를 포함한 주의 기록을 가져온다
    @PostMapping("/api/v1/records/{yearMonth}")
    public @ResponseBody List<RecordsListResponseDto> getYearMonthRecords
    (@PathVariable String yearMonth ) throws ParseException {
        String year = yearMonth.substring(0, 4);
        String month = yearMonth.substring(4);
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date today = transFormat.parse(year+"-"+month+"-00 00:00:00");
        System.out.println("getYearMonthRecords ---- "+today);
        List<RecordsListResponseDto> recordsListResponseDtos = recordsService.getYearMonthRecords(today);

        return recordsListResponseDtos;
    }
}
