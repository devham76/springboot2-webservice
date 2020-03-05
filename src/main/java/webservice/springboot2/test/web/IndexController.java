package webservice.springboot2.test.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import webservice.springboot2.test.config.auth.LoginUser;
import webservice.springboot2.test.config.auth.SessionUser;
import webservice.springboot2.test.domain.recruits.Recruits;
import webservice.springboot2.test.service.GolesService;
import webservice.springboot2.test.service.PostsService;
import webservice.springboot2.test.service.RecordsService;
import webservice.springboot2.test.service.RecruitsService;
import webservice.springboot2.test.web.dto.PostsDto.PostsResponseDto;
import webservice.springboot2.test.web.dto.RecordsDto.RecordsListResponseDto;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Controller // view return이 주목적, api와 view를 함께사용할때사용
public class IndexController {

    private final PostsService postsService;
    private final RecordsService recordsService;
    private final RecruitsService recruitsService;

    @GetMapping("/")
        public String index(Model model, @LoginUser SessionUser user){
            model.addAttribute("userName", user.getName());
            return "index";
        /*
            if (user != null) {
                model.addAttribute("userName", user.getName());
                return "index";
            }
            else {
                return "loginView";
            }

         */
    }
    @GetMapping("/loginView")
    public String loginView(){
        return "loginView";
    }


    @GetMapping("/posts/save")
    public String postsSave(Model model, @LoginUser SessionUser user){
        model.addAttribute("userName", user.getName());
        return "posts-save";
    }

    // 수정 화면
    @GetMapping("/posts/update/{id}")
    public  String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }

    // 주간관리 화면
    @GetMapping("/weekly")
    public String weekly(Model model){
        Date today = new Date(System.currentTimeMillis());
        System.out.println("--today : "+today);
        List<RecordsListResponseDto> recordsListResponseDtos = recordsService.findByRecordDateBetween(today);

        model.addAttribute("records",recordsListResponseDtos);

        return "weekly";
    }

    @GetMapping("/jobCalendar")
    public String jabCalendar(){
        return "jobCalendar";
    }

    // 채용정보 화면
    @GetMapping("/recruitInfo")
    public String recruitInfo(Model model) throws IOException {
        List<Recruits> recruitsList = recruitsService.getRecruitInfo();
        model.addAttribute("recruits",recruitsList);
        return "recruitInfo";
    }

    // 계획정보 화면
    @GetMapping("/planGole")
    public String planGole(Model model) throws JsonProcessingException {
        return "planGole";
    }
    @GetMapping("/posts")
    public String Posts(){
        return "posts";
    }

}
