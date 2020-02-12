package webservice.springboot2.test.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import webservice.springboot2.test.service.posts.UnderpinsService;
import webservice.springboot2.test.web.dto.UnderpinsListResponseDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UnderpinsApiController {
    private final UnderpinsService underpinsService;

    @GetMapping("/api/v1/underpins")
    public List<UnderpinsListResponseDto> getUnderpinsList(){
        System.out.println("[getUnderpinsList api start]...");
        List<UnderpinsListResponseDto> list =  underpinsService.findAllDesc();

        for(UnderpinsListResponseDto underpin:list){
            System.out.println(underpin);
        }
        return list;
    }
}
