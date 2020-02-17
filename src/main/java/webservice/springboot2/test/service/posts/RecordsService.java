package webservice.springboot2.test.service.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webservice.springboot2.test.domain.posts.Posts;
import webservice.springboot2.test.domain.records.Records;
import webservice.springboot2.test.domain.records.RecordsRepository;
import webservice.springboot2.test.web.dto.RecordsDto.RecordsListResponseDto;
import webservice.springboot2.test.web.dto.RecordsDto.RecordsRequestDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecordsService {

    private final RecordsRepository recordsRepository;

    // 트랜잭션 범위는 유지 / 조회속도개선 -> 등록,수정,삭제 없는 서비스메소드에서 사용 추천
    @Transactional(readOnly = true)
    public List<RecordsListResponseDto> findByRecordDateBetween(Date selectedDate){
        System.out.println("[findByRecordDateBetween start]--selectedDate="+selectedDate);
        Date start, end;
        Calendar calendar = Calendar.getInstance() ;
        calendar.setTime(selectedDate);

        // 선택된 날짜가 포함되어있는 월,일요일 구하기
        // 선택날짜가 월요일이면 start는 선택날짜
        int dayNum = calendar.get(Calendar.DAY_OF_WEEK);

        // 일요일이면 저번주 일요일로, 아니면 이번주 일요일로 이동
        int gotoMonday = (dayNum==1) ? -7 :(1-dayNum);
        calendar.add(Calendar.DATE, gotoMonday);
        start = (Date) calendar.getTime();

        // 종료날짜는 = 선택날짜 + 7 , 일요일까지
        calendar.add(Calendar.DATE, 7);
        end = (Date) calendar.getTime();

        System.out.println("---------------start="+start);
        System.out.println("---------------end="+end);
        // 시작,종료 날짜는 포함되지 않는다
        List<RecordsListResponseDto> recordsListResponseDtos = recordsRepository.findByRecordDateBetween(start, end).stream()
                .map(RecordsListResponseDto::new)
                .collect(Collectors.toList());


        setRecordsListResponseDtos(recordsListResponseDtos, start, end);

        // 날짜 순으로 정렬
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
        Collections.sort(recordsListResponseDtos, new Comparator<RecordsListResponseDto>() {
            public int compare(RecordsListResponseDto arg0, RecordsListResponseDto arg1) {
                String date1 = simpleDateFormat.format(arg0.getRecordDate());
                String date2 = simpleDateFormat.format(arg1.getRecordDate());
                return date1.compareTo(date2);
            }
        });
        System.out.println("RecordsListResponseDto  ==>");
        for(RecordsListResponseDto r:recordsListResponseDtos){
            System.out.println(r.getRecordDate());
        }
        return recordsListResponseDtos;
    }



    // 시작일 ~ 종료일까지의 모든 일정 인스턴스를 만들어서 반환한다
    private void setRecordsListResponseDtos(List<RecordsListResponseDto> recordsListResponseDtos,
                                            Date start, Date end) {

        // 시작일 재설정
        Calendar calendar = Calendar.getInstance() ;
        calendar.setTime(start);    // 시작일은 일요일이다
        calendar.add(Calendar.DATE, 1); // 시작일+1 => 월요일로 설정
        start = (Date) calendar.getTime();

        int index=0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd" );

        if(recordsListResponseDtos.isEmpty()){
            while(recordsListResponseDtos.size() < 7){
                RecordsListResponseDto newRecordsResponseDto = new RecordsListResponseDto(
                    Records.builder()
                            .recordDate(start)
                            .content("")
                            .hour(0)
                            .minute(0)
                            .build());
                recordsListResponseDtos.add(newRecordsResponseDto);
                // day+1
                calendar.add(Calendar.DATE, 1);
                start = (Date) calendar.getTime();
            }
        }
        else {
            System.out.println("size : "+recordsListResponseDtos.size() );
            while (recordsListResponseDtos.size() < 7) {
                System.out.print("[" + index + "]------- ");
                System.out.println(recordsListResponseDtos.get(index).getRecordDate());
                String recordsDate = simpleDateFormat.format(recordsListResponseDtos.get(index).getRecordDate());
                String startDate = simpleDateFormat.format(start);
                //System.out.println("[" + index + "]-------compare : " + recordsDate.compareTo(startDate));
                //System.out.println(recordsListResponseDtos.get(index).getRecordDate() + ", " + recordsDate);
                //System.out.println(start + ", " + startDate);

                if (recordsDate.compareTo(startDate) == 0) {
                    index++;
                } else {
                    RecordsListResponseDto newRecordsResponseDto = new RecordsListResponseDto(
                            Records.builder()
                                    .recordDate(start)
                                    .content("")
                                    .hour(0)
                                    .minute(0)
                                    .build());
                    recordsListResponseDtos.add(newRecordsResponseDto);
                }
                // day+1
                calendar.add(Calendar.DATE, 1);
                start = (Date) calendar.getTime();

            }
        }

    }
    @Transactional
    public Long save(RecordsRequestDto requestDto) {
        return recordsRepository.save(requestDto.toEntity()).getId();
    }
    @Transactional
    public Long update(Long id, RecordsRequestDto requestDto) {
        Records records = recordsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        records.update(requestDto.getContent(), requestDto.getHour(), requestDto.getMinute());
        return id;

    }

    @Transactional(readOnly = true)
    public List<RecordsListResponseDto> getYearMonthRecords(Date start) throws ParseException {
        // {"date":"2020-02-17","classname" : "grade-5"},
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance() ;
        calendar.setTime(start);

        int endYear = calendar.get(Calendar.YEAR);
        int endMonth = calendar.get(Calendar.MONTH)+1;
        int endDate  = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        String endString = endYear+"-"+endMonth+"-"+endDate+" 00:00:00";
        Date end = transFormat.parse(endString);
        System.out.println(end);

        System.out.println("first day="+start+" last day="+end);
        // 시작,종료 날짜는 포함되지 않는다
        List<RecordsListResponseDto> recordsListResponseDtos = recordsRepository.findByRecordDateBetween(start, end).stream()
                .map(RecordsListResponseDto::new)
                .collect(Collectors.toList());

        return recordsListResponseDtos;
    }
}
