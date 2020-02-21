package webservice.springboot2.test.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import webservice.springboot2.test.service.posts.GolesService;
import webservice.springboot2.test.web.dto.plansGolesDto.GolesSaveRequestDto;

@RequiredArgsConstructor
@RestController
public class PlanGoleApiContorller {
    private final GolesService golesService;

    @PostMapping("/api/v1/goles")
    public int saveGole(@RequestBody GolesSaveRequestDto golesSaveRequestDto){
        return golesService.save(golesSaveRequestDto);
    }

}
