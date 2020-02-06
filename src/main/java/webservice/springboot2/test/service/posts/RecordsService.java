package webservice.springboot2.test.service.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webservice.springboot2.test.domain.records.RecordsRepository;
import webservice.springboot2.test.web.dto.RecordsDto.RecordsListResponseDto;

import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecordsService {

    private final RecordsRepository recordsRepository;

    // 트랜잭션 범위는 유지 / 조회속도개선 -> 등록,수정,삭제 없는 서비스메소드에서 사용 추천
    @Transactional(readOnly = true)
    public List<RecordsListResponseDto> findByRecordDateBetween(Date selectedDate){
        Date start, end;
        Calendar calendar = Calendar.getInstance() ;
        calendar.setTime(selectedDate);

        // 선택된 날짜가 포함되어있는 월,일요일 구하기
        // 선택날짜가 월요일이면 start는 선택날짜
        int dayNum = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayNum == 2) {
            start = selectedDate;
        }
        // 아니라면 선택날짜가 포함되어있는 월요일 선택
        else{
            int gotoMonday = (dayNum==1) ? -7 :(2-dayNum);
            calendar.add(Calendar.DATE, gotoMonday);
            start = (Date) calendar.getTime();
        }
        // 종료날짜는 = 선택날짜 + 6 , 일요일까지
        calendar.add(Calendar.DATE, 6);
        end = (Date) calendar.getTime();

        System.out.println("---------------start="+start);
        System.out.println("---------------end="+end);

        return recordsRepository.findByRecordDateBetween(start, end).stream()
                .map(RecordsListResponseDto::new)
                .collect(Collectors.toList());
    }
}
