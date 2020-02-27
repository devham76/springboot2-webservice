package webservice.springboot2.test.domain.plansGoles;

import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GolesRepository extends JpaRepository<Goles, Integer> {
    @Query(value = "select a from Goles a where a.goleSeq = goleSeq")
    Goles findByGoleSeq(@Param("goleSeq") int goleSeq);
    //Option<Goles> findByGoleSeq(int goleSeq);

}
