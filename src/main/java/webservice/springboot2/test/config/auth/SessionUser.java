package webservice.springboot2.test.config.auth;

import lombok.Getter;
import webservice.springboot2.test.domain.user.User;

// User클래스는 엔티티이므로 다른 엔티티와 관계를 맺을가능성이 크다. 즉 변경가능성이 크므로
// 직렬 기능을 가진 세션 dto를 만들어주는게 좋은 방법이다
import java.io.Serializable;    // 직렬화

@Getter
/*
 *************************************************************************
 * [ class 설명 ]
 * 인증된 사용자 정보를 담는 클래스입니다
 * SessionUser에는 인증된 사용자 정보만 필요하므로 name,email,picture만 필드로 선언한다
 * ************************************************************************
 */
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
