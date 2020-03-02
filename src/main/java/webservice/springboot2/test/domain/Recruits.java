package webservice.springboot2.test.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.sql.DataSourceDefinition;

@NoArgsConstructor
@Getter
public class Recruits {
    private int idx;
    private String url;
    private String corpname;
    private String title;
    private String upjong;
    private String jcjong;
    private String keyword;
    private String worktype;
    private String pay;
    private String opendate;
    private String closedate;
    private String endtype;
    private String applytype;
    private String area;

    private String recommend;
    private String rate;
    private String ect;
    @Builder
    public Recruits(String url, String corpname, String title, String upjong, String jcjong,
                       String keyword, String worktype, String pay, String opendate, String closedate,
                       String endtype, String applytype, String area,
                       String recommend, String rate, String ect, int idx){
        this.url = url;
        this.corpname = corpname;
        this.title = title;
        this.upjong = upjong;
        this.jcjong = jcjong;
        this.keyword = keyword;
        this.worktype = worktype;
        this.pay = pay;
        this.opendate = opendate;
        this.closedate = closedate;
        this.endtype = endtype;
        this.applytype = applytype;
        this.area = area;
        this.idx = idx;
        this.recommend = recommend;
        this.rate = rate;
        this.ect = ect;

    }
}
