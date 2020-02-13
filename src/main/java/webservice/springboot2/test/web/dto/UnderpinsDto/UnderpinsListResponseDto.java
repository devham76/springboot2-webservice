package webservice.springboot2.test.web.dto.UnderpinsDto;

import lombok.Getter;
import webservice.springboot2.test.domain.underpins.Underpins;

@Getter
public class UnderpinsListResponseDto {
    private Long id;
    private String writer;
    private String content;
    private int isAppend;

    public UnderpinsListResponseDto(Underpins entity){
        this.id = entity.getId();
        this.writer = entity.getWriter();
        this.content = entity.getContent();
        this.isAppend = entity.getIsAppend();
    }
}
