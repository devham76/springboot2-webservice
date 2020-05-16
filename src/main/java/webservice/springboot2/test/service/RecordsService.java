package webservice.springboot2.test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webservice.springboot2.test.domain.records.Records;
import webservice.springboot2.test.domain.records.RecordsRepository;
import webservice.springboot2.test.web.dto.RecordsDto.RecordsListResponseDto;
import webservice.springboot2.test.web.dto.RecordsDto.RecordsMarkDto;
import webservice.springboot2.test.web.dto.RecordsDto.RecordsSaveRequestDto;
import webservice.springboot2.test.web.dto.RecordsDto.RecordsUpdateRequestDto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
/*
 *************************************************************************
 *  공부기록 CRUD를 하는 클래스입니다.
 *************************************************************************
 */
@RequiredArgsConstructor
@Service
public class RecordsService {

    private final RecordsRepository recordsRepository;

    // 트랜잭션 범위는 유지 / 조회속도개선 -> 등록,수정,삭제 없는 서비스메소드에서 사용 추천
    @Transactional(readOnly = true)
    public List<RecordsListResponseDto> findByRecordDateBetween(Date selectedDate){
        //System.out.println("[findByRecordDateBetween start]--selectedDate="+selectedDate);
        Date start, end;

        Calendar calendar = Calendar.getInstance() ;
        calendar.setTime(selectedDate);


        //-- 선택된 날짜가 포함되어있는 월요일 & 일요일 구하기
        // 1. 선택된 날짜의 요일 구하기
        int dayNum = calendar.get(Calendar.DAY_OF_WEEK);

        // 2. 선택된 날짜의 요일을 이용해서 시작 날짜 구하기.
        // 월요일이면, 시작날짜는 선택날짜
        // 일요일이면, 시작날짜는 저번주 일요일. 아니면 이번주 일요일로 이동
        int gotoMonday = (dayNum==1) ? -7 :(1-dayNum);
        calendar.add(Calendar.DATE, gotoMonday);
        start = (Date) calendar.getTime();

        // 종료날짜는 = 선택날짜 + 7  (=일요일까지)
        calendar.add(Calendar.DATE, 7);
        end = (Date) calendar.getTime();

        // 주의) 시작,종료 날짜는 포함되지 않는다
        List<RecordsListResponseDto> recordsListResponseDtos = recordsRepository.findByRecordDateBetween(start, end).stream()
                .map(RecordsListResponseDto::new)
                .collect(Collectors.toList());

        // 날짜 순으로 정렬
        Collections.sort(recordsListResponseDtos);

        // 1주일중에 빈 요일이 있으면 임의로 넣는다
        setRecordsListResponseDtos(recordsListResponseDtos, start, end);

        // 날짜 순으로 정렬
        Collections.sort(recordsListResponseDtos);

        return recordsListResponseDtos;
    }


    //----------------------------------------------
    // 시작일 ~ 종료일까지의 모든 일정 인스턴스를 만들어서 반환한다
    //----------------------------------------------
    private void setRecordsListResponseDtos(List<RecordsListResponseDto> recordsListResponseDtos,
                                            Date start, Date end) {

        // 시작일 재설정
        Calendar calendar = Calendar.getInstance() ;
        calendar.setTime(start);    // 시작일은 일요일이다
        calendar.add(Calendar.DATE, 1); // 시작일+1 => 월요일로 설정
        start = (Date) calendar.getTime();
        // util date to sql date
        java.sql.Date sqlDate = new java.sql.Date(start.getTime());

        int index=0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "yyyy-MM-dd" );

        if(recordsListResponseDtos.isEmpty()){
            while(recordsListResponseDtos.size() < 7){
                RecordsListResponseDto newRecordsResponseDto = new RecordsListResponseDto(
                    Records.builder()
                            .recordDate(sqlDate)
                            .content("")
                            .hour(0)
                            .minute(0)
                            .build());
                recordsListResponseDtos.add(newRecordsResponseDto);
                // day+1
                calendar.add(Calendar.DATE, 1);
                start = (Date) calendar.getTime();
                sqlDate = new java.sql.Date(start.getTime());
            }
        }
        else {
            //System.out.println("size : "+recordsListResponseDtos.size() );
            while (recordsListResponseDtos.size() < 7) {
                int dtoSize = recordsListResponseDtos.size();
                //System.out.print("[" + index + "]------- ");

                boolean needAdd = false;
                String startDate = simpleDateFormat.format(start).substring(0,10);
                if(index < dtoSize) {
                    String recordsDate = simpleDateFormat.format(recordsListResponseDtos.get(index).getRecordDate()).substring(0,10);
                    //System.out.println("startDate="+startDate+", recordsDate="+recordsDate);
                    if (recordsDate.compareTo(startDate) == 0) {
                        index++;
                    }
                    else    // list의 순서가 요일순서에 맞지 않으면 해당요일을 list에 넣는다
                        needAdd = true;

                }
                else    // 원래 list에 있는것들 검사 끝나면 나머지 나머지 요일을 넣는다
                    needAdd = true;

                if (needAdd) {
                    RecordsListResponseDto newRecordsResponseDto = new RecordsListResponseDto(
                            Records.builder()
                                    .recordDate(sqlDate)
                                    .content("")
                                    .hour(0)
                                    .minute(0)
                                    .build());
                    recordsListResponseDtos.add(newRecordsResponseDto);
                }
                // day+1
                calendar.add(Calendar.DATE, 1);
                start = (Date) calendar.getTime();
                sqlDate = new java.sql.Date(start.getTime());

            }
        }

    }
    @Transactional
    public Long save(RecordsSaveRequestDto requestDto) {
        return recordsRepository.save(requestDto.toEntity()).getId();
    }
    @Transactional
    public Long update(Long id, RecordsUpdateRequestDto requestDto) {
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
        //System.out.println(end);

        //System.out.println("first day="+start+" last day="+end);
        // 시작,종료 날짜는 포함되지 않는다
        List<RecordsListResponseDto> recordsListResponseDtos = recordsRepository.findByRecordDateBetween(start, end).stream()
                .map(RecordsListResponseDto::new)
                .collect(Collectors.toList());

        return recordsListResponseDtos;
    }

    @Transactional(readOnly = true)
    public List<RecordsMarkDto> findAllDesc() {
        return recordsRepository.findAllDesc().stream()
                .map(RecordsMarkDto::new)
                .collect(Collectors.toList());
    }

}
