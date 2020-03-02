package webservice.springboot2.test.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import webservice.springboot2.test.service.posts.GolesService;
import webservice.springboot2.test.service.posts.PlansService;
import webservice.springboot2.test.web.dto.plansGolesDto.GolesListResponseDto;
import webservice.springboot2.test.web.dto.plansGolesDto.GolesSaveRequestDto;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController // @Controller이 아닌이유: view 필요없이 단지api만 사용하므로. data return이목적
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
    @PostMapping("/api/v1/plan/save")
    public int savePlan(@RequestBody Map<String, String> json){
        String content = json.get("content");
        int goleSeq = Integer.parseInt(json.get("goleSeq"));
        return plansService.save(content, goleSeq);
    }
    @PutMapping("/api/v1/plan/update/{planSeq}")
    public int updatePlan(@PathVariable int planSeq, @RequestBody Map<String, String> json){
        String conent = json.get("content");
        int goleSeq = Integer.parseInt(json.get("goleSeq"));
        return plansService.update(planSeq, goleSeq, conent);
    }
    @DeleteMapping("/api/v1/gole/delete/{goleSeq}")
    public void delteGole(@PathVariable int goleSeq){
        golesService.delete(goleSeq);
    }
}

