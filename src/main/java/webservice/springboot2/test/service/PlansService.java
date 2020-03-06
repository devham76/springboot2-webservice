package webservice.springboot2.test.service;

import lombok.RequiredArgsConstructor;
import org.h2.table.Plan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webservice.springboot2.test.domain.plansGoles.Goles;
import webservice.springboot2.test.domain.plansGoles.GolesRepository;
import webservice.springboot2.test.domain.plansGoles.Plans;
import webservice.springboot2.test.domain.plansGoles.PlansRepository;
import webservice.springboot2.test.domain.posts.Posts;
import webservice.springboot2.test.web.dto.plansGolesDto.PlansSaveRequestDto;

import java.util.List;
/*
 *************************************************************************
 * 계획 설정 CRUD를 하는 클래스입니다.
 *************************************************************************
 */
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
    @Transactional
    public int update(int planSeq, int goleSeq, String conent) {
        // Plans 해당 객체 업데이트시 db업데이트 된다
        Plans plans = plansRepository.findById(planSeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 목표가 없습니다. id="+planSeq));
        plans.update(conent);

        // goles에 연결되어있는 plan 업데이트
        Goles goles = golesRepository.getOne(goleSeq);
        List<Plans> list = goles.getPlanList();
        for(Plans p : list){
            if(p.getPlanSeq() == planSeq)
                p.update(conent);
        }
        return planSeq;
    }
}
