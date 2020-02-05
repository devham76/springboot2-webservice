package webservice.springboot2.test.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import webservice.springboot2.test.domain.posts.Underpins;

@Getter
@NoArgsConstructor
public class UnderpinsUpdateRequestDto {
    private String content;

    @Builder
    public UnderpinsUpdateRequestDto(String content) {
        this.content = content;
    }
    public Underpins toEntity() {
        return Underpins.builder()
                .content(content)
                .build();
    }
}
