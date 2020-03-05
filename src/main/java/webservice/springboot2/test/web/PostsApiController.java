package webservice.springboot2.test.web;
import org.springframework.web.bind.annotation.*;
import webservice.springboot2.test.service.PostsService;
import webservice.springboot2.test.web.dto.PostsDto.PostsResponseDto;
import webservice.springboot2.test.web.dto.PostsDto.PostsSaveRequestDto;
import webservice.springboot2.test.web.dto.PostsDto.PostsUpdateRequestDto;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
// 의존성 자동으로 주입된다, 생성자로 의존성을 주입하다보면 에러발생할경우가 많기때문에 생성자로 주입하는게 안전
@RestController
// 해당 애노테이션 사용시, return 값이 http 응답헤더로 전달된다
public class PostsApiController {
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }

    // 게시글 수정
    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    // 게시글 삭제
    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id){
        postsService.delete(id);
        return id;
    }

}
