package webservice.springboot2.test.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import webservice.springboot2.test.domain.recruits.Recruits;
import webservice.springboot2.test.web.dto.RecruitsDto;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URL;
import java.util.*;

@Service
public class RecruitsService {
    public static final int recruitsInfoNum = 16;   // 채용정보의 속성은 16개
    public static int recuritsIndex;                // 채용정보 개수
    public List<Recruits> getRecruitInfo() throws IOException {
        recuritsIndex = 1;
        List<Recruits> recruitsList = new ArrayList<>();
        String[] addrList = {"http://api.career.co.kr/open?id=VjfzNMrb5o8yb/LgS8rsPQ==&jc=H002&ac1=1&ec=0"
                        ,"http://api.career.co.kr/open?id=VjfzNMrb5o8yb/LgS8rsPQ==&jc=H002&ac1=3&ec=0"};

        for (String addr : addrList) {
            String xml = getRecruitInfoXml(addr);                       // api 요청응답결과의 xml
            List<Recruits> subRecruitsList = getRecruitInfoList(xml);   // xml 분석 결과의 recruits 리스트
            recruitsList.addAll(subRecruitsList);
        }

        String url = "https://linkareer.com/list/recruit?activityOrderDirection=DESC&activityOrderField=VIEW_COUNT&activityTypeID=5&categoryIDs[0]=58&jobTypes[0]=NEW&jobTypes[1]=INTERN&regionIDs[0]=2&regionIDs[1]=9";
        CrawlingService linkareer = new CrawlingService(url);
        recruitsList.addAll(linkareer.crawlerLinkareer());
        String url2 = "https://www.jobplanet.co.kr/job_postings/search?utf8=%E2%9C%93&jp_min_overall=2.0&recruitment_type_ids%5B%5D=1&recruitment_type_ids%5B%5D=4&city_ids%5B%5D=1&city_ids%5B%5D=2&occupation_level2_ids%5B%5D=11604&order_by=score";
        CrawlingService jobplanet = new CrawlingService(url2);
        recruitsList.addAll(jobplanet.crawlerJobplanet());

        return recruitsList;
    }

    private List<Recruits> getRecruitInfoList(String xml) {
        String[] tagNames = {"url", "회사명", "title", "upjong", "jcjong", "keyword",
                "worktype", "pay", "opendate", "closedate",
                "endtype", "applytype", "area"};
        List<Recruits> subRecruitsList = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentbuilder = factory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(xml.getBytes());
            Document doc = documentbuilder.parse(is);
            Element element = doc.getDocumentElement();
            NodeList jobslList = element.getElementsByTagName("jobs");
            for (int i = 0; i < jobslList.getLength(); i++) {
                String[] nodeValueList = new String[recruitsInfoNum];
                for( int j=0; j<tagNames.length; j++) {
                    Node node = element.getElementsByTagName(tagNames[j]).item(i);
                    Node temp = node.getFirstChild();
                    nodeValueList[j] = temp.getNodeValue();
                }
                RecruitsDto recruitsDto = new RecruitsDto(nodeValueList, recuritsIndex++);
                subRecruitsList.add(recruitsDto.toEntity());
                System.out.println(recruitsDto.toString());
            }

        } catch (Exception e) {
            System.out.println("[getRecruitInfoList]error:" + e.getMessage());
        }


        return subRecruitsList;
    }

    private String getRecruitInfoXml(String addr) throws IOException {
        String xml="";
        URL url = new URL(addr);

        InputStream in = url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        String str;
        int index=0;
        while ((str = br.readLine()) != null) {
            if(index!=0) {
                xml+=str;
            }
            index++;
        }
        return xml;
    }



}
