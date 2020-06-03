package webservice.springboot2.test.domain.user;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// repository == DAO , JpaRepository<Entity클래스, PK타입>을 상속하면 기본적인 CRUD가 생성된다
// User의 CRUD를 책임진다. Dao라고 불리는 DB Layer 접근자이다(p.95)
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByIp(String ip);

}
