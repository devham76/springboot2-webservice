package webservice.springboot2.test.web.dto.plansGolesDto;

import lombok.Builder;
import webservice.springboot2.test.domain.plansGoles.Plans;

import java.util.List;

public class GolesUpdateRequestDto {

    private String title;
    private List<Plans> planList;

    @Builder
    public GolesUpdateRequestDto(String title, List<Plans> planList){
        this.title = title;
        this.planList = planList;
    }
}
