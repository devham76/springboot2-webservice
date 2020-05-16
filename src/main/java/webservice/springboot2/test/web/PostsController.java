package webservice.springboot2.test.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import webservice.springboot2.test.config.auth.LoginUser;
import webservice.springboot2.test.config.auth.SessionUser;
import webservice.springboot2.test.service.PostsService;
import webservice.springboot2.test.web.dto.PostsDto.PostsResponseDto;

@RequiredArgsConstructor
@Controller
public class PostsController {

    private final PostsService postsService;

    // 일지 관리 화면
    @GetMapping("/posts")
    public String Posts(Model model){
        model.addAttribute("posts", postsService.findAllDesc());
        return "posts";
    }

    // 일지 추가 화면
    @GetMapping("/posts/save")
    public String postsSave(Model model, @LoginUser SessionUser user){
        model.addAttribute("userName", user.getName());
        return "posts-save";
    }

    // 일지 수정 화면
    @GetMapping("/posts/update/{id}")
    public  String postsUpdate(@PathVariable Long id, Model model){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }
}
