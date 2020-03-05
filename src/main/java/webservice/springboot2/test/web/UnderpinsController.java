package webservice.springboot2.test.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import webservice.springboot2.test.service.UnderpinsService;
import webservice.springboot2.test.web.dto.UnderpinsDto.UnderpinsResponseDto;

@RequiredArgsConstructor
@Controller
public class UnderpinsController {

    private final UnderpinsService underpinsService;
    // 응원 글 관리화면
    @GetMapping("/underpins")
    public String underpin(Model model){
        model.addAttribute("underpins",underpinsService.findAllDesc());
        return "underpins";
    }
    // 응원글 등록화면
    @GetMapping("/underpins/save")
    public String underpinsSave(){
        return "underpins-save";
    }

    // 응원글 수정화면
    @GetMapping("/underpins/update/{id}")
    public String underpinsUpdate(@PathVariable Long id, Model model){
        UnderpinsResponseDto dto = underpinsService.findById(id);
        model.addAttribute("underpin", dto);
        return "underpins-update";
    }
}
