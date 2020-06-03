package webservice.springboot2.test.config.auth;
// config.auth : 시큐리티 관련 클래스는 모두 이 패키지에 담는다

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import webservice.springboot2.test.domain.user.Role;
/*
 *************************************************************************
 * [ class 설명 ]
 * secrurity 설정을 지정합니다
 * ************************************************************************
 */
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
                    .antMatchers( "/css/**",
                        "/images/**", "/js/**", "/h2-console/**",
                        "/profile", "/loginView", "/profile").permitAll()
                    // 권한 관리대상자를 지정하는 옵션.  permitAll():전체열람권한

                    // get요청은 로그인한 사용자만 허용
                    .antMatchers(HttpMethod.GET).authenticated()
                    // post, put, delete 요청은 인증된 사용자만 허용
                    .antMatchers(HttpMethod.POST).hasRole(Role.USER.name())
                    .antMatchers(HttpMethod.DELETE).hasRole(Role.USER.name())
                    .antMatchers(HttpMethod.PUT).hasRole(Role.USER.name())
                    .and()
                // 로그인 페이지 커스터마이즈
                .formLogin()
                    .loginPage("/loginView").permitAll()
                    .and()
                .logout()
                    .logoutSuccessUrl("/")  // 로그아웃 성공시 /로 이동
                    .and()
                .oauth2Login() // 로그인 기능의 설정
                    .userInfoEndpoint() //로그인 성공이후 사용자 정보를 가져올 때 설정담당
                        .userService(customOAuth2UserService);
    }
}
