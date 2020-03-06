
package webservice.springboot2.test.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/*
 *************************************************************************
 * [ interface 설명 ]
 * LoginUser 어노테이션을 사용할 수 있도록 지정합니다
 * login 세션값을 가져오는 어노테이션입니다
 * ************************************************************************
 */
// 해당 어노테이션이 생성될 수 있는 위치를 지정
// PARAMETER:메소드의 파라미터로 선언된 객체에서만 사용가능
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {   // @interface : 이 파일을 LoginUser라는 이름의 어노테이션으로 지정.
}
