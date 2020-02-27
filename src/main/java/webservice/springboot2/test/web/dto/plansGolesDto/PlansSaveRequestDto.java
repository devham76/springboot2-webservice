package webservice.springboot2.test.web.dto.plansGolesDto;

import lombok.Builder;
import org.h2.table.Plan;
import webservice.springboot2.test.domain.plansGoles.Goles;
import webservice.springboot2.test.domain.plansGoles.Plans;

public class PlansSaveRequestDto {

    private String content;
    private Goles goles;

    @Builder
    public PlansSaveRequestDto(String content, Goles goles){
        this.goles = goles;
        this.content = content;
    }
    public Plans toEntity(){
        return Plans.builder()
                .goles(goles)
                .content(content)
                .build();
    }
}
