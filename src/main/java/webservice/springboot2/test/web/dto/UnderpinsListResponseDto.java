package webservice.springboot2.test.web.dto;

import lombok.Getter;
import webservice.springboot2.test.domain.posts.Underpins;

import java.time.LocalDateTime;

@Getter
public class UnderpinsListResponseDto {
    private Long id;
    private String content;
    private int isDelete;

    public UnderpinsListResponseDto(Underpins entity){
        this.id = entity.getId();
        this.content = entity.getContent();
        this.isDelete = entity.getIsDelete();
    }
}
