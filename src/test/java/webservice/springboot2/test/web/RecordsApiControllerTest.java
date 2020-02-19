package webservice.springboot2.test.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import webservice.springboot2.test.domain.posts.Posts;
import webservice.springboot2.test.domain.records.Records;
import webservice.springboot2.test.domain.records.RecordsRepository;
import webservice.springboot2.test.web.dto.PostsSaveRequestDto;
import webservice.springboot2.test.web.dto.RecordsDto.RecordsSaveRequestDto;
import webservice.springboot2.test.web.dto.RecordsDto.RecordsUpdateRequestDto;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecordsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private RecordsRepository recordsRepository;

    @After
    public void tearDown(){
        recordsRepository.deleteAll();
    }

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Records_save() throws Exception {
        //=== given
        String content = "content";
        Date recordDate = new Date();
        int hour = 3;
        int minute = 10;
        // builder를 이용해서 객체 생성한다
        RecordsSaveRequestDto requestDto = RecordsSaveRequestDto.builder()
                .content(content)
                .recordDate(recordDate)
                .hour(hour)
                .minute(minute)
                .build();

        String url = "http://localhost:" + port + "/api/v1/recordSave";

        //=== when
        // 생성된 MockMvc를 통해 API를 테스트한다
        // 본문(BODY)영역은 문자열로 표현하기 위해 ObjectMapper를 통해 문자열 json으로 변환한다
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //=== then
        List<Records> all = recordsRepository.findAll();
        assertThat(all.get(0).getRecordDate()).isEqualTo(recordDate);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }


}
