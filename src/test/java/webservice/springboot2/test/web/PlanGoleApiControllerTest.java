package webservice.springboot2.test.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import webservice.springboot2.test.domain.plansGoles.Goles;
import webservice.springboot2.test.domain.plansGoles.GolesRepository;
import webservice.springboot2.test.web.dto.plansGolesDto.GolesSaveRequestDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlanGoleApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private GolesRepository golesRepository;

    // IoC컨테이너에 있는 빈을 주입받아서 사용한다
    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;
    @Before
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    // test완료 후에 저장된 정보 다지운다
    @After
    public void tearDowm(){
        golesRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Gole_저장되는지확인() throws Exception {
        //== given
        String title = "title";
        GolesSaveRequestDto golesSaveRequestDto = GolesSaveRequestDto.builder().title(title).build();

        String url = "http://localhost:" + port + "/api/v1/gole";

        //== when
        // 생성된 MockMvc를 통해 API를 테스트한다
        // 본문(BODY)영역은 문자열로 표현하기 위해 ObjectMapper를 통해 문자열 json으로 변환한다
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                // objectmapper가 직렬화 할때 golesSaveRequestDto의 @Getter이필요하다
                .content(new ObjectMapper().writeValueAsString(golesSaveRequestDto)))
                .andExpect(status().isOk());
        List<Goles> golesList = golesRepository.findAll();
        assertThat(golesList.get(0).getTitle()).isEqualTo(title);
    }
}
/*
*    @PostMapping("/api/v1/gole")
    public int saveGole(@RequestBody GolesSaveRequestDto golesSaveRequestDto){
        return golesService.save(golesSaveRequestDto);
    }
* */