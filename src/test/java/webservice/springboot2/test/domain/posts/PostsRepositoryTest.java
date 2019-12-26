package webservice.springboot2.test.domain.posts;

import javafx.geometry.Pos;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)    // 스프링부트테스트와 JUnit의 연결자/JUnit에 내장된 실행자 외에 다른 실행자를 실행시킨다
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;


    /*
    * 1. Junit에서 단위 테스트가 끝날 때마다 수행되는 메소드를 지정
    * 2. 여러 테스트가 동시에 수행되면 테스트용 db인 H2에 데이터가
    * 그대로 남아 있어 다음테스트 실행시 테스트가 실패할 수 있으므로 테스트 후 모두 삭제해준다.
    */
    @After
    public void cleanup() {
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기(){
        //given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        // builder를 이용해 최종값을 채운다
        // save : id값이 있다면 update, 없다면 insert
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("devham76@gmail.com")
                .build());

        // when
        // table post에 있는 모든 데이터 조회
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);
        // astertThat : 검증 메소드. 검증하고 싶은 대상을 인자로 받는다.
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }
}
