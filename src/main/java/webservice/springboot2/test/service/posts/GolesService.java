package webservice.springboot2.test.service.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webservice.springboot2.test.domain.plansGoles.GolesRepository;
import webservice.springboot2.test.web.dto.plansGolesDto.GolesListResponseDto;
import webservice.springboot2.test.web.dto.plansGolesDto.GolesSaveRequestDto;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor    // 파라메터로 들어오는 객체에 의존성 주입해준다
@Service
public class GolesService {

    private final GolesRepository golesRepository;

    @Transactional(readOnly = true)
    public List<GolesListResponseDto> findAllSeq() {
        Sort sort = new Sort(Sort.Direction.ASC, "GoleSeq");    // orderby goleseq asc
        return golesRepository.findAll(sort).stream()
                .map(GolesListResponseDto::new)
                .collect(Collectors.toList());
    }
    @Transactional
    public int save(GolesSaveRequestDto golesSaveRequestDto) {
        System.out.println("[ service save ] title = "+golesSaveRequestDto.getTitle());
        int goleseq = golesRepository.save(golesSaveRequestDto.toEntity()).getGoleSeq();
        System.out.println("[ service save ]goleseq = "+goleseq);
        return goleseq;
    }
}
