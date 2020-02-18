package webservice.springboot2.test.service.posts;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import webservice.springboot2.test.web.dto.RecruitsDto;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.URL;
import java.util.*;

@Service
public class RecruitsService {

    public List<RecruitsDto> getRecruitInfo() throws IOException {
        List<RecruitsDto> list = null;
        String addr = "http://api.career.co.kr/open?id=VjfzNMrb5o8yb/LgS8rsPQ==&jc=H002&ac1=1&ec=1";
        URL url = new URL(addr);


        InputStream in = url.openStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

        String str;
        String xml="";
        int idx=0;
        while ((str = br.readLine()) != null) {
            if(idx!=0) {
                System.out.println(str);
                xml+=str;
            }
            idx++;
        }
        System.out.println("-------");

        List<String> data = new ArrayList<String>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentbuilder = factory.newDocumentBuilder(); //// 占쏙옙占썰리 占쌨쇽옙占쏙옙 占쏙옙占쏙옙  占쏙옙占썲에占쏙옙 占쏙옙占쏙옙占�
            InputStream is = new ByteArrayInputStream(xml.getBytes());
            Document doc = documentbuilder.parse(is);
            Element element = doc.getDocumentElement();

            NodeList urlList = element.getElementsByTagName("url");
            for (int i = 0; i < urlList.getLength(); i++) {

                Node node = urlList.item(i);

                Node temp = node.getFirstChild();
                String value = temp.getNodeValue();
                data.add(value);

            }

        } catch (Exception e) {
            System.out.println(  e.getMessage());
        }

        for(String imsi : data){
            System.out.println(imsi);
        }

        return list;
    }
}
