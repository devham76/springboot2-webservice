package webservice.springboot2.test.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import webservice.springboot2.test.domain.plansGoles.Goles;
import webservice.springboot2.test.service.posts.GolesService;
import webservice.springboot2.test.service.posts.PlansService;
import webservice.springboot2.test.web.dto.plansGolesDto.GolesListResponseDto;
import webservice.springboot2.test.web.dto.plansGolesDto.GolesSaveRequestDto;
import webservice.springboot2.test.web.dto.plansGolesDto.PlansSaveRequestDto;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class PlanGoleApiContorller {
    private final GolesService golesService;
    private final PlansService plansService;

    @PostMapping("/api/v1/goles")
    public int saveGole(@RequestBody GolesSaveRequestDto golesSaveRequestDto){
        return golesService.save(golesSaveRequestDto);
    }
    @GetMapping("/api/v1/planGole")
    public List<GolesListResponseDto> findAllGoles() {
        return golesService.findAllSeq();
    }
    //public List<RecordsListResponseDto> getWeeklyRecords
    //            (@RequestBody Map<String, String> json) throws ParseException {
    @PostMapping("/api/v1/planSave")
    public int savePlan(@RequestBody Map<String, String> json){
        String content = json.get("content");
        int goleSeq = Integer.parseInt(json.get("goleSeq"));

        return plansService.save(content, goleSeq);
    }
}
