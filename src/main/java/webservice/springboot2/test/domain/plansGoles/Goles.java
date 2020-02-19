package webservice.springboot2.test.domain.plansGoles;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor // @Entity는 무조건 기본생성자가 존재해야 한다
@Entity
public class Goles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int goleSeq;

    @Column(length = 500, nullable = false)
    private String title;

    @OneToMany(fetch=FetchType.EAGER    // 항상 plane목록을 가져오게 된다
            , cascade = CascadeType.ALL)
    @JoinColumn(name="planSeq")
    private List<Plans> planList;

    @Builder
    public Goles(String title){ // 처음부터 계획이 생성되지 않으므로 생성시에는 제목만 추가해준다
        this.title = title;
    }
    public void addPlane(Plans plan){
        if(planList == null)
            planList = new ArrayList<>();

        planList.add(plan);
    }
    public String toString(){
        String result = "[GOLE SEQ = "+goleSeq+"]";
        for(Plans p:planList){
            result += p.toString();
        }
        return result;
    }

}
