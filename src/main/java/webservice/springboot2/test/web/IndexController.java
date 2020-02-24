package webservice.springboot2.test.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import webservice.springboot2.test.config.auth.LoginUser;
import webservice.springboot2.test.config.auth.SessionUser;
import webservice.springboot2.test.domain.Recruits;
import webservice.springboot2.test.service.posts.GolesService;
import webservice.springboot2.test.service.posts.PostsService;
import webservice.springboot2.test.service.posts.RecordsService;
import webservice.springboot2.test.service.posts.RecruitsService;
import webservice.springboot2.test.web.dto.PostsDto.PostsResponseDto;
import webservice.springboot2.test.web.dto.RecordsDto.RecordsListResponseDto;
import webservice.springboot2.test.web.dto.plansGolesDto.GolesListResponseDto;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final RecordsService recordsService;
    private final RecruitsService recruitsService;
    private final GolesService golesSerice;
    private final HttpSession httpSession;

    @GetMapping("/")
        public String index(Model model, @LoginUser SessionUser user){
        // Model : 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다
        // postsService.findAllDesc()의 결과를 posts로 index.mustache에 전달한다
        model.addAttribute("posts",postsService.findAllDesc());

        // 로그인 성공시 CustomOAuth2UserService에서 SessionUser를 저장하도록 하였으므로
        // 로그인 성공 시 httpSeesion.getAttribute("user")에서 값을 가져올수있다
        // SessionUser user = (SessionUser) httpSession.getAttribute("user"); -> 파라미터 @LoginUser 로 처리함
        if (user != null){
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(){
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
        //============================
        // Recruits , RecruitsDto  따로 쓰는 이유 ???
        //============================
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
