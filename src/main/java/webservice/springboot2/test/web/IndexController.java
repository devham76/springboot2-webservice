package webservice.springboot2.test.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
/*
 *************************************************************************
 * view return 하는 Controller입니다
 *************************************************************************
 */
@RequiredArgsConstructor
@Controller
public class IndexController {


    private final RecordsService recordsService;
    private final RecruitsService recruitsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        // 로그인 했다면
        if (user != null) {
            model.addAttribute("userName", user.getName());

            return "index";
        }
        // 로그인 안했으면 로그인창으로 이동
        return "loginView";

    }

    // 로그인 화면
    @GetMapping("/loginView")
    public String loginView(){
        return "loginView";
    }



    // records 관리 화면
    @GetMapping("/weekly")
    public String weekly(Model model){
        Date today = new Date(System.currentTimeMillis());
        List<RecordsListResponseDto> recordsListResponseDtos = recordsService.findByRecordDateBetween(today);

        model.addAttribute("records",recordsListResponseDtos);

        return "weekly";
    }

    // 채용달력 화면
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


}
