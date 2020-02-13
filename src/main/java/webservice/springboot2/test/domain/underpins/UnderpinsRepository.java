package webservice.springboot2.test.domain.underpins;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UnderpinsRepository extends  JpaRepository<Underpins, Long>{
    @Query("SELECT u FROM Underpins u ORDER BY u.id DESC")
    List<Underpins> findAllDesc();
}
