package webservice.springboot2.test.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter // getter생성
// 선언된 모든 final 필드가 포함된 생성자를 생성해줍니다.
// final이 없는 필드는 생성자에 포함되지 않습니다.
@RequiredArgsConstructor
// 사용자의 권한을 관리할 Enum 클래스
public enum Role {

    GUEST("ROLE_GEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;

}
