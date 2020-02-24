package webservice.springboot2.test.domain.plansGoles;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Plans {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int planSeq;

    @Column(nullable = false)
    private int goleSeq;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Builder
    public Plans(String content, int goleSeq){
        this.content = content;
        this.goleSeq = goleSeq;
    }
    public String toString(){
        return "["+planSeq+" in Gole("+goleSeq+")] : "+content;
    }
}
