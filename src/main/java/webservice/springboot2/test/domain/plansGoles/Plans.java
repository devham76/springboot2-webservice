package webservice.springboot2.test.domain.plansGoles;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Plans {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int planSeq;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @JsonBackReference  // 순환참조 방지. Goles를 참조하면 Goles는 다시 Plans를 참조하지 않게막는다
    @ManyToOne
    @JoinColumn(name = "goleSeq")
    private Goles goles;

    public void update(String content){
        this.content = content;
    }
    @Builder
    public Plans(String content, Goles goles){
        this.content = content;
        this.goles = goles;
    }
    public String toString(){
        return "["+planSeq+" ] : "+content;
    }
}
