package webservice.springboot2.test.web.dto;

import lombok.Builder;
import lombok.Getter;
import webservice.springboot2.test.domain.Recruits;

@Getter
public class RecruitsDto {
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

    @Builder
    public RecruitsDto(String url, String corpname, String title, String upjong, String jcjong,
                       String keyword, String worktype, String pay, String opendate, String closedate,
                       String endtype, String applytype, String area, int idx){

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
    }
    @Builder
    public RecruitsDto(String[] values, int idx){
        this.url = values[0];
        this.corpname = values[1];
        this.title = values[2];
        this.upjong = values[3];
        this.jcjong = values[4];
        this.keyword = values[5];
        this.worktype = values[6];
        this.pay = values[7];
        this.opendate = values[8];
        this.closedate = values[9];
        this.endtype = values[10];
        this.applytype = values[11];
        this.area = values[12];
        this.idx = idx;
    }

    public String toString(){
        return url+"/ "+corpname+"/ "+title+"/ "+upjong+"/ "+jcjong+"/ "+keyword+"/ "+
                worktype+"/ "+pay+"/ "+opendate+"/ "+closedate+"/ "+endtype+"/ "+applytype+"/ "+area;
    }
    public Recruits toEntity(){
        return Recruits.builder()
                .url(url)
                .corpname(corpname)
                .title(title)
                .upjong(upjong)
                .jcjong(jcjong)
                .keyword(keyword)
                .worktype(worktype)
                .pay(pay)
                .opendate(opendate)
                .closedate(closedate)
                .endtype(endtype)
                .applytype(applytype)
                .area(area)
                .idx(idx)
                .build();
    }
}
