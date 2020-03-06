package webservice.springboot2.test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webservice.springboot2.test.domain.plansGoles.Goles;
import webservice.springboot2.test.domain.plansGoles.GolesRepository;
import webservice.springboot2.test.web.dto.plansGolesDto.GolesListResponseDto;
import webservice.springboot2.test.web.dto.plansGolesDto.GolesSaveRequestDto;

import java.util.List;
import java.util.stream.Collectors;
/*
 *************************************************************************
 * 목표 설정 CRUD를 하는 클래스입니다.
 *************************************************************************
 */
@RequiredArgsConstructor    // 생성자의 파라메터로 들어오는 인자에 의존성부여
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

    public void delete(int goleSeq) {
        Goles goles = golesRepository.findById(goleSeq)
            .orElseThrow(() -> new IllegalArgumentException("해당 목표가 없습니다. id="+goleSeq));
        golesRepository.delete(goles);
    }
    public int update(int goleSeq, String title) {
        Goles goles = golesRepository.findById(goleSeq)
                .orElseThrow(() -> new IllegalArgumentException("해당 목표가 없습니다. id="+goleSeq));
        goles.update(title);
        return goleSeq;
    }
}
