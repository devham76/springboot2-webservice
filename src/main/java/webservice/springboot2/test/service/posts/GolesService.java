package webservice.springboot2.test.service.posts;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import webservice.springboot2.test.domain.plansGoles.GolesRepository;
import webservice.springboot2.test.web.dto.PostsDto.PostsListResponseDto;
import webservice.springboot2.test.web.dto.plansGolesDto.GolesListResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GolesService {
    private GolesRepository golesRepository;
    public List<GolesListResponseDto> findAllSeq() {
        Sort sort = new Sort(Sort.Direction.ASC, "GoleSeq");
        return golesRepository.findAll(sort).stream()
                .map(GolesListResponseDto::new)
                .collect(Collectors.toList());
    }
}
