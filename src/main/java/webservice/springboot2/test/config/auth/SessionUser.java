package webservice.springboot2.test.config.auth;

import lombok.Getter;
import webservice.springboot2.test.domain.user.User;

import java.io.Serializable;

@Getter
// SessionUser에는 인증된 사용자 정보만 필요하므로 name,email,picture만 필드로 선언한다
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
