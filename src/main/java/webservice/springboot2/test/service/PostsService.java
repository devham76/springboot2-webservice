package webservice.springboot2.test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import webservice.springboot2.test.domain.posts.Posts;
import webservice.springboot2.test.domain.posts.PostsRepository;
import webservice.springboot2.test.web.dto.PostsDto.PostsResponseDto;
import webservice.springboot2.test.web.dto.PostsDto.PostsSaveRequestDto;
import webservice.springboot2.test.web.dto.PostsDto.PostsUpdateRequestDto;
import webservice.springboot2.test.web.dto.PostsDto.PostsListResponseDto;

import java.util.List;
import java.util.stream.Collectors;

/*
 *************************************************************************
 * 일지작성 CRUD를 하는 클래스입니다.
 *************************************************************************
 */
@RequiredArgsConstructor    // final이 선언된 모든 필드를 인자값으로 하는 생성자를 생성해준다
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id="+id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional
    public Long delete(Long id){
        // 존재하는  Posts인지 확인을 위해 엔티티 조회 후
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));
        // 삭제
        postsRepository.delete(posts);

        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id="+id));

        return new PostsResponseDto(entity);
    }

    // 트랜잭션 범위는 유지 / 조회속도개선 -> 등록,수정,삭제 없는 서비스메소드에서 사용 추천
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }
}
