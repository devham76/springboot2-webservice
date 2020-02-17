package webservice.springboot2.test.domain.records;

// 공부 기록 entity

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Getter
@NoArgsConstructor

@Entity
public class Records {
    // 구분 seq값
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // pk생성규칙, auto_increment
    private Long id;

    // 작성한 회원
    /* 나중에 추가해볼것
    @Column(nullable = false)
    private Long userId;
    */

    // 공부한 날짜
    @Column(nullable = false)
    private Date recordDate;

    // 공부한 시간
    @Column
    private int hour;

    // 공부한 분
    @Column
    private int minute;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    // 해당 클래스의 빌더 패턴 클래스를 생성
    @Builder
    public Records(String content, Date recordDate, int hour, int minute) {
        this.content = content;
        this.recordDate = recordDate;
        this.hour = hour;
        this.minute = minute;
    }

    public void update(String content, int hour, int minute) {
        this.content = content;
        this.hour = hour;
        this.minute = minute;
    }
}
