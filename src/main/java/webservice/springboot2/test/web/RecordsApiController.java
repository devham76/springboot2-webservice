package webservice.springboot2.test.web;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import webservice.springboot2.test.service.posts.RecordsService;
import webservice.springboot2.test.web.dto.RecordsDto.RecordsListResponseDto;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class RecordsApiController {

    private RecordsService recordsService;

    @GetMapping("/api/v1/records")
    public Model getWeeklyRecords(@RequestParam String selectedDate, Model model){
        Date today = new Date(System.currentTimeMillis());
        System.out.println("--selectedDate : "+today);
        List<RecordsListResponseDto> recordsListResponseDtos = recordsService.findByRecordDateBetween(today);

        model.addAttribute("records",recordsListResponseDtos);

        for(RecordsListResponseDto data: recordsListResponseDtos)
            System.out.println("!!!!!!------>>>"+data.getContent());

        return model;
    }

}
