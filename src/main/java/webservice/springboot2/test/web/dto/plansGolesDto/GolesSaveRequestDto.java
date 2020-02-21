package webservice.springboot2.test.web.dto.plansGolesDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import webservice.springboot2.test.domain.plansGoles.Goles;

@Getter
@NoArgsConstructor
public class GolesSaveRequestDto {
    private String title;

    @Builder
    public GolesSaveRequestDto(String title){
        this.title = title;
    }

    public Goles toEntity(){
        return Goles.builder()
                .title(title)
                .build();
    }
}
