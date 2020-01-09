package webservice.springboot2.test.config.auth.dto;
/*
OAuth:
인터넷 사용자들이 비밀번호를 제공하지 않고
다른 웹사이트(구글,네이버등) 상의 자신들의 정보에 대해
웹사이트나 애플리케이션의 접근 권한을 부여할 수 있는 공통적인 수단으로서 사용되는,
접근 위임을 위한 개방형 표준이다. -> 회사에 입장할때 사원증(ID,PW)과 방문증 (OAuth)
* */
import lombok.Builder;
import lombok.Getter;
import webservice.springboot2.test.domain.user.Role;
import webservice.springboot2.test.domain.user.User;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    //-------------------
    //-- google
    //-------------------
    // OAuth2User에서 변환하는 사용자 정보는 Map이기 때문에 값 하나하나를 변환해야 한다.
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }
    public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes){
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    //-------------------
    //-- naver
    //-------------------
    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    // User엔티티를 생성한다
    // OAuthAttributes에서 엔티티를 생성하는 시점은 처음 가입할때이다
    // 기본권한은 GUEST
    // OAuthAttributes클래스 생성이 끝나면 같은 패키지의 SessionUser클래스를 생성한다
    public User toEntity(){
        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
