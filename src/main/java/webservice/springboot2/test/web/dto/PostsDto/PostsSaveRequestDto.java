package webservice.springboot2.test.web.dto.PostsDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import webservice.springboot2.test.domain.posts.Posts;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    // Request, Response용 DTO는 VIEW를 위한 클래스이므로 자주 변경되기 때문에
    // Entity 클래스(Posts)와 분리해서 사용해야 한다.
    // 저장용 dto 객체

    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
