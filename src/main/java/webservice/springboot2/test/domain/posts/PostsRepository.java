package webservice.springboot2.test.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

// Dao라고 불리는 DB Layer 접근자이다(p.95)
// <Entity 클래스, PK 타입>
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
