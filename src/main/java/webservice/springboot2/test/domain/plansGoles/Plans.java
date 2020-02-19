package webservice.springboot2.test.domain.plansGoles;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dom4j.swing.XMLTableColumnDefinition;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Plans {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int planSeq;

    @Column
    private int goleSeq;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    public Plans(String content){
        this.content = content;
    }
    public String toString(){
        return "["+planSeq+" in Gole("+goleSeq+")] : "+content;
    }
}
