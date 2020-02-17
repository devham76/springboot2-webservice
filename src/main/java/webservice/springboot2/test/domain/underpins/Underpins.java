package webservice.springboot2.test.domain.underpins;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor

@Entity
public class Underpins {

    @Id // pk필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) // pk생성규칙, auto_increment
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String writer;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "integer default 1")
    private int isAppend;

    // 해당 클래스의 빌더 패턴 클래스를 생성
    @Builder
    public Underpins(String writer, String content, int isAppend) {
        this.writer = writer;
        this.content = content;
        this.isAppend = isAppend;
    }

    public void update(String content, int isAppend){
        this.content = content;
        this.isAppend = isAppend;
    }
}
