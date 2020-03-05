package webservice.springboot2.test.web;

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
import webservice.springboot2.test.domain.plansGoles.Plans;
import webservice.springboot2.test.domain.plansGoles.PlansRepository;
import webservice.springboot2.test.service.GolesService;
import webservice.springboot2.test.service.PlansService;
import webservice.springboot2.test.web.dto.plansGolesDto.GolesListResponseDto;
import webservice.springboot2.test.web.dto.plansGolesDto.GolesSaveRequestDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class PlanGoleApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private GolesService golesService;
    @Autowired
    private PlansService plansService;

    @Autowired
    private GolesRepository golesRepository;
    @Autowired
    private PlansRepository plansRepository;

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
        String title = "test title";
        GolesSaveRequestDto golesSaveRequestDto = new GolesSaveRequestDto(title);
        String url = "http://localhost:" + port + "/api/v1/goles";

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

    @Test
    @WithMockUser(roles = "USER")
    public void gole에plan이_저장되는지확인() {
        // 목표저장
        Goles goles = Goles.builder().title("title").build();
        golesRepository.save(goles);
        List<Goles> list = golesRepository.findAll();
        int goleseq = list.get(0).getGoleSeq();

        // 계획저장
        Plans plans1 = Plans.builder().content("content1").goles(goles).build();
        Plans plans2 = Plans.builder().content("content2").goles(goles).build();
        goles.addPlan(plans1);
        goles.addPlan(plans2);

        plansRepository.save(plans1);
        plansRepository.save(plans2);

        // 목표와 계획이 연결되어있는지 확인
        List<Plans> plansList = plansRepository.findAll();
        assertThat(plansList.get(0).getGoles().getGoleSeq()).isEqualTo(goleseq);
        assertThat(plansList.get(1).getGoles().getGoleSeq()).isEqualTo(goleseq);

    }
    @Test
    @WithMockUser(roles = "USER")
    public void findAllseq() {
        String title = "title test";
        golesRepository.save(Goles.builder().title(title).build());
        List<GolesListResponseDto> list = golesService.findAllSeq();
        assertThat(list.get(0).getTitle()).isEqualTo(title);
    }
    @Test
    @WithMockUser(roles = "USER")
    public void plan저장시_gole에연결되는지_확인(){
        String title = "gole title";
        GolesSaveRequestDto golesSaveRequestDto = GolesSaveRequestDto.builder().title(title).build();
        int goleSeq = golesService.save(golesSaveRequestDto);
        //golesRepository.save(golesSaveRequestDto.toEntity());
        System.out.println("goleseq = "+goleSeq);

        String content = "plan content";
        int planSeq = plansService.save(content, goleSeq);
        Plans plan = plansRepository.findByPlanSeq(planSeq);
        assertThat(plan.getGoles().getGoleSeq()).isEqualTo(goleSeq);

         title = "gole title";
         golesSaveRequestDto = GolesSaveRequestDto.builder().title(title).build();
         goleSeq = golesService.save(golesSaveRequestDto);
        //golesRepository.save(golesSaveRequestDto.toEntity());
        System.out.println("goleseq2 = "+goleSeq);

         content = "plan content";
         planSeq = plansService.save(content, goleSeq);
         plan = plansRepository.findByPlanSeq(planSeq);
        assertThat(plan.getGoles().getGoleSeq()).isEqualTo(goleSeq);
    }
    @Test
    @WithMockUser(roles = "USER")
    public void gole삭제시_plan도_삭제되는지확인(){
        //-- given
        int goleSeq = golesService.save(GolesSaveRequestDto.builder().title("title").build());
        int planSeq1 = plansService.save("plan1", goleSeq);
        int planSeq2 = plansService.save("plan2", goleSeq);

        //-- when
        Goles goles = golesRepository.findByGoleSeq(goleSeq);
        golesRepository.delete(goles);
        Plans plans1 = plansRepository.findByPlanSeq(planSeq1);
        Plans plans2 = plansRepository.findByPlanSeq(planSeq2);

        assertThat(plans1).isNull();
        assertThat(plans2).isNull();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void jpaTest_oneToMany가작동하는지_확인한다() {
        //-- given
        // gole 객체생성, repository 저장
        // plan 객체생성, repository 저장 x , gole에 addPlan()
        // 이때 plan은 jpa에 의해 저장이 되었는지 확인한다
        Goles gole1 = new Goles("title");
        Plans plan1 = new Plans("계획 내용", gole1);
        gole1.addPlan(plan1);
        golesRepository.save(gole1);
        // plan 객체만 수정
        plansService.update(plan1.getPlanSeq(), gole1.getGoleSeq(), "계획 내용 변경");

        List<Plans> plansList = plansRepository.findAll();
        Plans plan = plansList.get(0);
        assertThat(plan.getContent()).isEqualTo("계획 내용 변경");

    }
}