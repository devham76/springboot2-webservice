package webservice.springboot2.test.domain.records;

import org.springframework.data.jpa.repository.JpaRepository;



import java.util.Date;
import java.util.List;

public interface RecordsRepository extends JpaRepository<Records, Long> {
    // 회원 아이디별로 찾기
   // Optional<User> findByUserid(Long id);

    List<Records> findByRecordDateBetween(Date start, Date end);
}
