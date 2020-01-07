package webservice.springboot2.test.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // JPA Entity 클래스들이 BaseTimeEntity를 상속할 경우 필드들(createdDate, modifiedDate)도 컬럼으로 인식하도록 한다
@EntityListeners(AuditingEntityListener.class)  // BaseTimeEntity 클래스에 Auditing기능(감사)을 포함시킴
public abstract class BaseTimeEntity {
    /*
    *  해당 클래스는 모든 Entity의 상위 클래스가 되어
    *  Entity들의 createdDate, modifiedDate를 자동으로 관리하는 역할이다
    * */

    @CreatedDate    // Entity가 생성되어 저장될때 시간 자동 저장된다
    private LocalDateTime createdDate;

    @LastModifiedDate   // Entity가 수정될때 시간 자동 저장된다
    private LocalDateTime modifiedDate;
}
