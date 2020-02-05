package webservice.springboot2.test.domain.posts;

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
    private String content;

    @Column(columnDefinition = "integer default 0")
    private int isDelete;

    // 해당 클래스의 빌더 패턴 클래스를 생성
    @Builder
    public Underpins(String content) {
        this.content = content;
        this.isDelete = 0;
    }
    public void update(String content){
        this.content = content;
    }
    public void delete(){
        this.isDelete = 1;
    }
}
