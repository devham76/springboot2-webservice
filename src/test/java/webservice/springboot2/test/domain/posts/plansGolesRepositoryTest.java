package webservice.springboot2.test.domain.posts;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import webservice.springboot2.test.domain.plansGoles.Goles;
import webservice.springboot2.test.domain.plansGoles.GolesRepository;
import webservice.springboot2.test.domain.plansGoles.Plans;
import webservice.springboot2.test.domain.plansGoles.PlansRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class) // 스프링부트 테스트와 JUnit사이의 연결자 역할
@SpringBootTest
public class plansGolesRepositoryTest {

    @Autowired
    GolesRepository golesRepository;

    @Autowired
    PlansRepository plansRepository;

    @Test
    public void jpaTest_onToMany가작동하는지_확인한다(){
        //-- given
        Goles gole1 = new Goles("title");
        Plans plan1 = new Plans("계획 내용");
        gole1.addPlane(plan1);
        // -- 저장
        golesRepository.save(gole1);

        List<Goles> golesList = golesRepository.findAll();
        Goles getGole = golesList.get(0);
        assertThat(getGole.getPlanList().get(0).getContent()).isEqualTo("계획 내용");
    }
    @Test
    public void Gole_plan없어도_괜찮은지(){
        Goles goles = new Goles("title");
        golesRepository.save(goles);
        List<Goles> golesList = golesRepository.findAll();
        Goles getGole = golesList.get(0);
        assertThat(getGole.getPlanList().size()).isEqualTo(0);
    }
}
