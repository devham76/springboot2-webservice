package webservice.springboot2.test.service.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webservice.springboot2.test.domain.plansGoles.Goles;
import webservice.springboot2.test.domain.plansGoles.GolesRepository;
import webservice.springboot2.test.domain.plansGoles.Plans;
import webservice.springboot2.test.domain.plansGoles.PlansRepository;
import webservice.springboot2.test.web.dto.plansGolesDto.PlansSaveRequestDto;

@RequiredArgsConstructor    // 생성자의 파라메터로 들어오는 인자에 의존성부여
@Service
public class PlansService {
    private final PlansRepository plansRepository;
    private final GolesRepository golesRepository;
    @Transactional
    public int save(String content, int goleSeq) {
        Goles goles = golesRepository.getOne(goleSeq);

        // Plans객체만 생성한다고 insert되는건 아니다
        Plans newPlans = PlansSaveRequestDto.builder()
                .content(content)
                .goles(goles)
                .build().toEntity();


        // gole에 addplan해도 작동된다
        goles.addPlan(newPlans);
        //repositoy.save작동한다
        return plansRepository.save(newPlans).getPlanSeq();
    }
}
