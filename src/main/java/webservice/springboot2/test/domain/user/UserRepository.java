package webservice.springboot2.test.domain.user;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// User의 CRUD를 책임진다. Dao라고 불리는 DB Layer 접근자이다(p.95)
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
