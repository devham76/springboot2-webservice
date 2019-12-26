package webservice.springboot2.test.service.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webservice.springboot2.test.domain.posts.PostsRepository;
import webservice.springboot2.test.web.dto.PostsSaveRequestDto;

@RequiredArgsConstructor    // final이 선언된 모든 필드를 인자값으로 하는 생성자를 생성해준다
@Service
// service : 도메인 순서 정의
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }
}
