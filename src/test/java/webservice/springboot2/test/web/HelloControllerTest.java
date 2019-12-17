package webservice.springboot2.test.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)    // 스프링 부터 테스트와 JUnit사이의 연결자 역할
@WebMvcTest // Web(Spring MVC)에 집중할 수 있는 어노테이션

public class HelloControllerTest {
    @Autowired  // 스프링이 관리하는 빈(Bean)을 주입 받는다
    private MockMvc mvc;
    // 웹API를 테스트할 때 사용, 스프링 MVC테스트의 시작점, 이 클래스를 통해 http get,post등에 대한 API테스트 가능

    @Test
    public void hello가_리턴된다() throws Exception{
        String hello = "hello";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }

    @Test
    public void hellowDto가_리턴된다() throws Exception{
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                .param("name",name) // API테스트할때 사용될 요청 파라메터(STRING만허용)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))    //JSON응답값을 필드별로 검증할수있는메소드
                .andExpect(jsonPath("$.amount", is(amount)));

    }
}
