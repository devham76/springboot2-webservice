package webservice.springboot2.test.service.posts;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import webservice.springboot2.test.domain.Recruits;
import webservice.springboot2.test.web.dto.RecruitsDto;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Service
public class RecruitsService {
    public static String[] tagNames = {"url", "회사명", "title", "upjong", "jcjong", "keyword",
            "worktype", "pay", "opendate", "closedate",
            "endtype", "applytype", "area"};
    public static int idx;
    public List<Recruits> getRecruitInfo() throws IOException {

        List<Recruits> recruitsList = new ArrayList<>();
        String[] addrList = {"http://api.career.co.kr/open?id=VjfzNMrb5o8yb/LgS8rsPQ==&jc=H002&ac1=1&ec=0"
                        ,"http://api.career.co.kr/open?id=VjfzNMrb5o8yb/LgS8rsPQ==&jc=H002&ac1=3&ec=0"};

        idx = 0;
        for (String addr : addrList) {
            String xml = getRecruitInfoXml(addr);   // api 요청응답결과의 xml
            List<Recruits> subRecruitsList = getRecruitInfoList(xml);   // xml 분석 결과의 recruits 리스트
            recruitsList.addAll(subRecruitsList);

        }
        return recruitsList;
    }

    private List<Recruits> getRecruitInfoList(String xml) {

        List<Recruits> subRecruitsList = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentbuilder = factory.newDocumentBuilder();
            InputStream is = new ByteArrayInputStream(xml.getBytes());
            Document doc = documentbuilder.parse(is);
            Element element = doc.getDocumentElement();
            NodeList jobslList = element.getElementsByTagName("jobs");
            for (int i = 0; i < jobslList.getLength(); i++) {
                String[] nodeValueList = new String[tagNames.length];

                for( int j=0; j<tagNames.length; j++) {
                    Node node = element.getElementsByTagName(tagNames[j]).item(i);
                    Node temp = node.getFirstChild();
                    nodeValueList[j] = temp.getNodeValue();
                }
                RecruitsDto recruitsDto = new RecruitsDto(nodeValueList, ++idx);
                subRecruitsList.add(recruitsDto.toEntity());
                System.out.println(recruitsDto.toString()+" /// "+nodeValueList[11]+": "+recruitsDto.getArea());

            }

        } catch (Exception e) {
            System.out.println("error:" + e.getMessage());
        }


        return subRecruitsList;
    }

    private String getRecruitInfoXml(String addr) throws IOException {
        String xml="";
        URL url = new URL(addr);

        InputStream in = url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        String str;
        int idx=0;
        while ((str = br.readLine()) != null) {
            if(idx!=0) {
                xml+=str;
            }
            idx++;
        }
        return xml;
    }
}
