package webservice.springboot2.test.web.dto.plansGolesDto;

import lombok.Builder;
import lombok.Getter;
import webservice.springboot2.test.domain.plansGoles.Goles;
import webservice.springboot2.test.domain.plansGoles.Plans;

import java.util.List;

@Getter
public class GolesListResponseDto {
    private int goleSeq;
    private String title;
    private List<Plans> planList;

    @Builder
    public GolesListResponseDto(Goles goles){
        this.goleSeq = goles.getGoleSeq();
        this.title = goles.getTitle();
        this.planList = goles.getPlanList();
    }
}
