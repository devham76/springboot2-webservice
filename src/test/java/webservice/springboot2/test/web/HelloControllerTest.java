package webservice.springboot2.test.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import webservice.springboot2.test.config.auth.SecurityConfig;


import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)    // 스프링부트 테스트와 JUnit사이의 연결자 역할
// @WebMvcTest // Web(Spring MVC)에 집중할 수 있는 어노테이션
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
        }
)

public class HelloControllerTest {

    @Autowired  // 스프링이 관리하는 빈(Bean)을 주입 받는다
    private MockMvc mvc;
    // 웹API를 테스트할 때 사용, 스프링 MVC테스트의 시작점, 이 클래스를 통해 http get,post등에 대한 API테스트 가능

    // 가짜로 인증된 사용자를 사용한다
    @WithMockUser(roles="USER")
    @Test
    public void hello가_리턴된다() throws Exception{
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @WithMockUser(roles="USER")
    @Test
    public void helloDto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        .param("name", name) // API테스트할때 사용될 요청 파라메터(STRING만허용)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))    //JSON응답값을 필드별로 검증할수있는메소드
                .andExpect(jsonPath("$.amount", is(amount)));

    }
}
