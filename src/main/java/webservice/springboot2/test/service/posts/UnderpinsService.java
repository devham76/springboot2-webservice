package webservice.springboot2.test.service.posts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webservice.springboot2.test.domain.posts.Underpins;
import webservice.springboot2.test.domain.posts.UnderpinsRepository;
import webservice.springboot2.test.web.dto.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service    // controller는 DAO(Repository)에 직접 접근하지 않고 service를 이용하여 요청을 처리한다
public class UnderpinsService {
    private final UnderpinsRepository underpinsRepository;

    @Transactional
    public Long save(UnderpinsSaveRequestDto requestDto) {
        return underpinsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, UnderpinsUpdateRequestDto requestDto){
        Underpins underpins = underpinsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 응원글이 없습니다. id="+id));
        underpins.update(requestDto.getContent());

        return id;
    }

    @Transactional
    public Long delete(Long id){
        // 존재하는  Posts인지 확인을 위해 엔티티 조회 후
        Underpins underpins = underpinsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 응원글이 없습니다. id="+id));
        // 삭제
        underpins.delete();
        return id;
    }

    public UnderpinsResponseDto findById(Long id) {
        Underpins entity = underpinsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 응원글이 없습니다. id="+id));

        return new UnderpinsResponseDto(entity);
    }

    // 트랜잭션 범위는 유지 / 조회속도개선 -> 등록,수정,삭제 없는 서비스메소드에서 사용 추천
    @Transactional(readOnly = true)
    public List<UnderpinsListResponseDto> findAllDesc(){
        return underpinsRepository.findAllDesc().stream()
                .map(UnderpinsListResponseDto::new)
                .collect(Collectors.toList());
    }
}