package webservice.springboot2.test.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import webservice.springboot2.test.service.GolesService;
import webservice.springboot2.test.service.PlansService;
import webservice.springboot2.test.web.dto.plansGolesDto.GolesListResponseDto;
import webservice.springboot2.test.web.dto.plansGolesDto.GolesSaveRequestDto;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController // @Controller이 아닌이유: view 필요없이 단지api만 사용하므로. data return이목적
public class PlanGoleApiContorller {

    private final GolesService golesService;
    private final PlansService plansService;

    // 목표 저장
    @PostMapping("/api/v1/goles")
    public int saveGole(@RequestBody GolesSaveRequestDto golesSaveRequestDto){
        return golesService.save(golesSaveRequestDto);
    }

    // 목표 + 계획 가져오기
    @GetMapping("/api/v1/planGole")
    public List<GolesListResponseDto> findAllGoles() {
        return golesService.findAllSeq();
    }

    // 계획 저장
    @PostMapping("/api/v1/plan/save")
    public int savePlan(@RequestBody Map<String, String> json){
        String content = json.get("content");
        int goleSeq = Integer.parseInt(json.get("goleSeq"));
        return plansService.save(content, goleSeq);
    }

    // 계획 수정
    @PutMapping("/api/v1/plan/update/{planSeq}")
    public int updatePlan(@PathVariable int planSeq, @RequestBody Map<String, String> json){
        String conent = json.get("content");
        int goleSeq = Integer.parseInt(json.get("goleSeq"));
        return plansService.update(planSeq, goleSeq, conent);
    }

    // 목표 삭제 (연관된 계획도 함께 삭제된다)
    @DeleteMapping("/api/v1/gole/delete/{goleSeq}")
    public int delteGole(@PathVariable int goleSeq){
        golesService.delete(goleSeq);
        return goleSeq;
    }

    // 목표 수정
    @PutMapping("/api/v1/gole/update/{goleSeq}")
    public int updateGole(@PathVariable int goleSeq, @RequestBody Map<String, String> json){
        String title = json.get("title");
        return golesService.update(goleSeq, title);
    }
}

