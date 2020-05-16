package webservice.springboot2.test.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import webservice.springboot2.test.domain.BaseTimeEntity;

import javax.persistence.*;

// lombok의 어노테이션
@Getter // 자동으로 getter생성
@NoArgsConstructor // 기본 생성자 자동 추가

@Entity // 테이블과 링크 될 클래스임을 알린다
// 사용자 저장 담당 Entity
public class User extends BaseTimeEntity {

    @Id // pk 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) // pk 생성규칙, auto_increment
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)    // JPA로 데이터베이스에 저장할때 Enum값을 String형태로 저장하여 무슨 코드를 의미하는지 확인
    @Column(nullable = false)
    private Role role;

    // 해당 클래스의 빌더 패턴 클래스를 생성
    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
