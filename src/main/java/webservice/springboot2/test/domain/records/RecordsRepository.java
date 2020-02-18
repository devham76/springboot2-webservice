package webservice.springboot2.test.domain.records;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface RecordsRepository extends JpaRepository<Records, Long> {
    // 회원 아이디별로 찾기
   // Optional<User> findByUserid(Long id);
    @Query("SELECT p FROM Records p ORDER BY p.id DESC")
    List<Records> findAllDesc();

    List<Records> findByRecordDateBetween(Date start, Date end);
}
