package webservice.springboot2.test.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import webservice.springboot2.test.service.posts.RecordsService;
import webservice.springboot2.test.web.dto.RecordsDto.RecordsListResponseDto;
import webservice.springboot2.test.web.dto.RecordsDto.RecordsMarkDto;
import webservice.springboot2.test.web.dto.RecordsDto.RecordsSaveRequestDto;
import webservice.springboot2.test.web.dto.RecordsDto.RecordsUpdateRequestDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
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

    /*
    계속 안됬던 이유
    records 등록,수정시에
    recordsSaveRequestDto 객체 생성자에 파라메터를 records 객체로지정했기 때문에
    컨트롤러의 requestbody에서 제대로 매핑되지 못했다
    */
    // 글 저장
    @PostMapping("/api/v1/recordSave")
    public Long saveRecord(@RequestBody RecordsSaveRequestDto requestDto){
            return recordsService.save(requestDto);
    }
    // 글 수정
    @PostMapping("/api/v1/recordUpdate/{id}")
    public Long updateRecord(@PathVariable Long id, @RequestBody RecordsUpdateRequestDto requestDto){
        return recordsService.update(id, requestDto);
    }
    // 해당 날짜에 공부한 시간을 색으로 표현한다
    @GetMapping("/api/v1/recordsForMark")
    public List<RecordsMarkDto> recordsForMark() {
        List<RecordsMarkDto> recordsMarkDtos = recordsService.findAllDesc();
        return recordsMarkDtos;
    }
}
