package webservice.springboot2.test.web;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import webservice.springboot2.test.service.UnderpinsService;
import webservice.springboot2.test.web.dto.UnderpinsDto.UnderpinsListResponseDto;
import webservice.springboot2.test.web.dto.UnderpinsDto.UnderpinsResponseDto;
import webservice.springboot2.test.web.dto.UnderpinsDto.UnderpinsSaveRequestDto;
import webservice.springboot2.test.web.dto.UnderpinsDto.UnderpinsUpdateRequestDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UnderpinsApiController {
    private final UnderpinsService underpinsService;
    // 응원글 모든 목록 가져오기
    /*
    @GetMapping("/api/v1/underpins")
    public List<UnderpinsListResponseDto> getUnderpinsList(){
        System.out.println("[getUnderpinsList api start]...");
        List<UnderpinsListResponseDto> list =  underpinsService.findAllDesc();

        for(UnderpinsListResponseDto underpin:list){
            System.out.println(underpin);
        }
        return list;
    }
    */

    // 적용할 응원글 목록 가져오기
    @GetMapping("/api/v1/underpins")
    public List<UnderpinsListResponseDto> findByWillAppend(){
        List<UnderpinsListResponseDto> list =  underpinsService.findByWillAppend();
        /*
        for(UnderpinsListResponseDto underpin:list){
            System.out.println(underpin);
        }
        */
        return list;
    }
    // 응원글 등록
    @PostMapping("/api/v1/underpins")
    public Long underpinsSaveApi(@RequestBody UnderpinsSaveRequestDto requestDto) {
        return underpinsService.save(requestDto);
    }

    // id로 응원글정보 가져오기
    @GetMapping("/api/v1/underpins/{id}")
    public UnderpinsResponseDto underpinsFindById(@PathVariable Long id){
        return underpinsService.findById(id);
    }

    // id 응원글 수정하기
    @PutMapping("/api/v1/underpins/{id}")
    public Long underpinsUpdateApi(@PathVariable Long id, @RequestBody UnderpinsUpdateRequestDto requestDto){
        return underpinsService.update(id, requestDto);
    }

    // id 응원글 삭제하기
    @DeleteMapping("/api/v1/underpins/{id}")
    public Long underpinsDeleteApi(@PathVariable Long id){
        underpinsService.delete(id);
        return id;
    }
}
