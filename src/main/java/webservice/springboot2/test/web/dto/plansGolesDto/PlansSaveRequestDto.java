package webservice.springboot2.test.web.dto.plansGolesDto;

import lombok.Builder;

public class PlansSaveRequestDto {

    private int goleSeq;
    private String content;

    @Builder
    public PlansSaveRequestDto(int goleSeq, String content){
        this.goleSeq = goleSeq;
        this.content = content;
    }
}
