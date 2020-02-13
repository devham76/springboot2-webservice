package webservice.springboot2.test.web.dto.UnderpinsDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import webservice.springboot2.test.domain.underpins.Underpins;

@Getter
@NoArgsConstructor
public class UnderpinsSaveRequestDto {
    private String writer;
    private String content;
    private int isAppend;

    @Builder
    public UnderpinsSaveRequestDto(String writer, String content, int isAppend) {
        this.writer = writer;
        this.content = content;
        this.isAppend = isAppend;
    }
    public Underpins toEntity() {
        return Underpins.builder()
                .writer(writer)
                .content(content)
                .isAppend(isAppend)
                .build();
    }
}
