package com.note.hyowon.blog.service;

import com.note.hyowon.blog.dto.PageRequestDTO;
import com.note.hyowon.blog.dto.PageResultDTO;
import com.note.hyowon.blog.dto.PostDTO;
import com.note.hyowon.blog.entity.PostEntity;
import com.note.hyowon.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor // 의존성 자동 주입
public class PostServiceImpl implements PostService{

    private final PostRepository repository;

    @Override
    public PageResultDTO<PostDTO, PostEntity> selectList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("postNo").descending());
        Page<PostEntity> result = repository.findAll(pageable);

        Function<PostEntity, PostDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public PostDTO selectOne(Integer postNo) {

        Optional<PostEntity> optionalEntity = repository.findById(postNo.longValue());

        if(!optionalEntity.isPresent()) {
            throw new IllegalArgumentException();
        }

        return entityToDto(optionalEntity.get());
    }

    @Override
    public Long insert(PostDTO dto) {

        log.info("PostDTO insert -----------");
        log.info(dto);

        PostEntity entity = dtoToEntity(dto);

        log.info(entity);

        repository.save(entity);

        return entity.getPostNo();
    }

}
