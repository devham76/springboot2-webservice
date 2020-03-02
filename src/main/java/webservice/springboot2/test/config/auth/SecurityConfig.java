package webservice.springboot2.test.config.auth;
// config.auth : 시큐리티 관련 클래스는 모두 이 패키지에 담는다

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import webservice.springboot2.test.domain.user.Role;

@RequiredArgsConstructor // final 필드가 포함된 생성자
@EnableWebSecurity  // spring security 설정들을 활성화시켜준다
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()   // h2-console 화면을 사용하기 위해 해당 옵션들을 disable합니다.
        .and()
                .authorizeRequests()    // URL별 관리를 설정하는 옵션의 시작점, 이부분이 선언되어야만 andMatchers옵션 사용 가능
                .antMatchers("/", "/css/**",
                        "/images/**", "/js/**", "/h2-console/**",
                        "/profile", "/login")
                .permitAll()
                // 권한 관리대상자를 지정하는 옵션. URL,HTTP 메소드별로 관리가능, permitAll():전체열람권한(윗줄)

                // post메소드이면서 api/v1/**주소를 가진 API는 USER권한을 가진 사람만 가능하다
                //.antMatchers("/api/v1/**").hasRole(Role.USER.name())  // 개발중에만 주석처리!!!!
                .anyRequest().authenticated()     // 설정된 값들 이외의 URL , 여기서는 authenticated를 이용하여 인증된 사용자에게만 나머지 url허용
        .and()
                .logout()
                .logoutSuccessUrl("/")  // 로그아웃 성공시 /로 이동
        .and()
                .oauth2Login() // 로그인 기능의 설정
                    .userInfoEndpoint() //로그인 성공이후 사용자 정보를 가져올 때 설정담당
                        .userService(customOAuth2UserService);
    }
}
