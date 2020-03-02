package webservice.springboot2.test.service.posts;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import webservice.springboot2.test.domain.Recruits;
import webservice.springboot2.test.web.dto.RecruitsDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static webservice.springboot2.test.service.posts.RecruitsService.recruitsInfoNum;
import static webservice.springboot2.test.service.posts.RecruitsService.recuritsIndex;


public class CrawlingService {
    private String url;
    private Document doc = null;
    CrawlingService(String url){
        try {
            doc = Jsoup.connect(url).get();
            this.url = url;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public List<Recruits> crawlerLinkareer(){
        List<Recruits> recruitList = new ArrayList<>();
        Elements elements = doc.select("div.MuiBox-root.jss208");
        // System.out.println(element);

        System.out.println("============================================================");
        Iterator<Element> ie1 = elements.select("div.MuiBox-root.jss208").iterator();


        while (ie1.hasNext()) {
            Element element = ie1.next();
            Element linkTag = element.select("a").first();
            String href = linkTag.attr("href");
            Iterator<Element> dataList = element.select("p.MuiTypography-root").iterator();
            System.out.println("[" + recuritsIndex + "] https://linkareer.com" + href);
            int idx = 0;
            String[] values = new String[recruitsInfoNum];
            values[0] = "https://linkareer.com" + href;
            while (dataList.hasNext()) {
                String data = dataList.next().text();
                if (!data.equals("?") && !data.equals("•")) {
                    System.out.print("[" + data + "]" + " / ");
                    switch (idx) {
                        case 0:	// corpname
                            values[1] = data;
                            break;
                        case 1:	// title
                            values[2] = data;
                            break;
                        case 2:	// jcjong
                            values[4] = data;
                            break;
                        case 3:	// applytype
                            values[11] = data;
                            break;
                        case 4:	// area
                            values[12] = data;
                            break;
                        case 6:	// closeDate -> ~3.01 / 상시모집 / 채용시마감
                            values[9] = data;
                            break;
                    }
                    idx++;
                }

            }
            recruitList.add(new RecruitsDto(values,recuritsIndex).toEntity());
            System.out.println();
            recuritsIndex++;
        }

        System.out.println("============================================================");
        return recruitList;
    }
    public List<Recruits> crawlerJobplanet(){
        List<Recruits> recruitList = new ArrayList<>();
        Elements elements = doc.select("div .job_content");

        Iterator<Element> dataList = elements.select("div.result_unit_info").iterator();

        while (dataList.hasNext()) {
            String[] values = new String[recruitsInfoNum];
            Element data = dataList.next();
            // url
            values[0] = "https://www.jobplanet.co.kr" + data.select("div.result_unit_info .posting_name").attr("href");
            // corpname
            values[1] = data.select("div.result_unit_info .company_name a").text();
            // title
            values[2] = data.select("div.result_unit_info .posting_name").text();
            // close date
            values[9] = data.select("div.result_unit_info .d_day").text();	// D-3, 채용시 마감, 상시채용, 오늘

            // pay
            values[7] = data.select("div.result_unit_info .jp_data_set .salary").text();
            // rate
            values[14]= data.select("div.result_unit_info .jp_data_set .rate").text();
            // recommend
            values[13] = data.select("div.result_unit_info .jp_data_set .recommend").text();
            // ect
            String ect = data.select("div.result_unit_info .result_labels").text();
            ect.replace("더보기", "");
            values[15] = ect;
            recruitList.add(new RecruitsDto(values,recuritsIndex).toEntity());
            recuritsIndex++;
        }

        return recruitList;
    }
}
