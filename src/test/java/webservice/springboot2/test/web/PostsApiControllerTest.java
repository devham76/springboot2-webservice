package webservice.springboot2.test.web;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import webservice.springboot2.test.domain.posts.Posts;
import webservice.springboot2.test.domain.posts.PostsRepository;
import webservice.springboot2.test.web.dto.PostsSaveRequestDto;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import webservice.springboot2.test.web.dto.PostsUpdateRequestDto;

import java.time.LocalDateTime;
import java.util.List;

import  static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {
    @LocalServerPort
    private int port;

    // JAP기능까지 한번에 테스트 할때는 @SpringBootTest와 TestRestTemplate를 사용한다
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    //  Junit에서 단위 테스트가 끝날 때마다 수행되는 메소드를 지정, 혼선방지를 위해 data를 모두 지운다
    @After
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    // IoC컨테이너에 있는 빈을 주입받아서 사용한다
    @Autowired
    private WebApplicationContext context;

    // 웹 애플리케이션을 애플리케이션 서버에 배포하지 않고도 스프링 MVC의 동작을 재현할 수 있는 클래스
    // 애플리케이션 서버에 배포한 것과 같은 것 처럼 테스트 가능
    private MockMvc mvc;

    // 매번 테스트가 시작되기 전에 MockMvc 인스턴스를 생성한다
    @Before
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles="USER") // 인증된 가짜 사용자를 만들어 사용한다, roles에 권한을 준다
    // 즉, 이것으로인해 ROLE_USER권한을 가진 사용자가 API를 요청하는 것과 같다
    // posts_등록된다
    public void Posts_save() throws Exception {
        //=== given
        String title = "title";
        String content = "content";
        // builder를 이용해서 객체 생성한다
        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts";

        //=== when
        // 생성된 MockMvc를 통해 API를 테스트한다
        // ㅂㄴ문(BODY)영역은 문자열로 표현하기 위해 ObjectMapper를 통해 문자열 json으로 변환한다
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //=== then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);
    }

    @Test
    @WithMockUser(roles="USER")
    // Posts_수정된다
    public void Posts_edit() throws Exception {
        //given
        Posts savedPosts = postsRepository.save(Posts.builder()
                                .title("title")
                                .content("content")
                                .author("author")
                                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
                .title(expectedTitle)
                .content(expectedContent)
                .build();

        String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

        HttpEntity<PostsUpdateRequestDto> requestEntity = new HttpEntity<>(requestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
    }

    @Test
    public void BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2019,12,27,0,0,0);
        postsRepository.save(Posts.builder()
                    .title("Title")
                    .content("Content")
                    .author("devham")
                    .build());

        //when
        List<Posts> postsList = postsRepository.findAll();

        //then
        Posts posts = postsList.get(0);

        System.out.println(">>>>> createDate="+posts.getCreatedDate()+", modifiedDate="+posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }

}
