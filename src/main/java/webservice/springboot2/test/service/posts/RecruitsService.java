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
import java.net.URL;
import java.util.*;

@Service
public class RecruitsService {

    public List<Recruits> getRecruitInfo() throws IOException {
        List<Recruits> recruitsList = new ArrayList<>();
        String addr = "http://api.career.co.kr/open?id=VjfzNMrb5o8yb/LgS8rsPQ==&jc=H002&ac1=1&ec=1";
        URL url = new URL(addr);

        InputStream in = url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

        String str;
        String xml="";
        int idx=0;
        while ((str = br.readLine()) != null) {
            if(idx!=0) {
                //System.out.println(str);
                xml+=str;
            }
            idx++;
        }
        //System.out.println("-------");

        List<String> data = new ArrayList<>();

        String[] tagNames = {"url", "회사명", "title", "upjong", "jcjong", "keyword",
                "worktype", "pay", "opendate", "closedate",
                "endtype", "applytype", "area"};
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentbuilder = factory.newDocumentBuilder(); //// 占쏙옙占썰리 占쌨쇽옙占쏙옙 占쏙옙占쏙옙  占쏙옙占썲에占쏙옙 占쏙옙占쏙옙占�
            InputStream is = new ByteArrayInputStream(xml.getBytes());
            Document doc = documentbuilder.parse(is);
            Element element = doc.getDocumentElement();
            //"url, 회사명, title, upjong, jcjong, worktype,
            NodeList jobslList = element.getElementsByTagName("jobs");
            for (int i = 0; i < jobslList.getLength(); i++) {
                System.out.println("--["+i+"]--");
                String[] nodeValueList = new String[tagNames.length];

                for( int j=0; j<tagNames.length; j++) {
                    Node node = element.getElementsByTagName(tagNames[j]).item(i);
                    Node temp = node.getFirstChild();
                    nodeValueList[j] = temp.getNodeValue();
                }
                RecruitsDto recruitsDto = new RecruitsDto(nodeValueList[0],nodeValueList[1], nodeValueList[2],
                        nodeValueList[3],nodeValueList[4],nodeValueList[5],nodeValueList[6],nodeValueList[7],
                        nodeValueList[8],nodeValueList[9],nodeValueList[10],nodeValueList[11]);
                recruitsList.add(recruitsDto.toEntity());
                //System.out.println(recruitsDto.toString());

            }

        } catch (Exception e) {
            System.out.println("error:" + e.getMessage());
        }


        return recruitsList;
    }
}
