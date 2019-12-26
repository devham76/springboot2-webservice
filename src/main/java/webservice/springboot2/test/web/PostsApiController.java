package webservice.springboot2.test.web;

import org.springframework.web.bind.annotation.PutMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import webservice.springboot2.test.service.posts.PostsService;
import webservice.springboot2.test.web.dto.PostsSaveRequestDto;


@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;

    @PutMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }
}
