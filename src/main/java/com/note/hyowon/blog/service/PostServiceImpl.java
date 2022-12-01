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

/**
 * processes CRUD and others related to post
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository repository;

    /**
     * paging and return list of posts
     *
     * @param requestDTO contains request data like postNo..
     * @return PageResultDTO<PostDTO, PostEntity>
     */
    @Override
    public PageResultDTO<PostDTO, PostEntity> selectList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("postNo").descending());
        Page<PostEntity> result = repository.findAll(pageable);

        Function<PostEntity, PostDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    /**
     * finds a post requested from a client
     *
     * @param postNo used to find a post
     * @return PostDTO contains data of a selected post
     */
    @Override
    public PostDTO selectOne(Integer postNo) {

        Optional<PostEntity> optionalEntity = repository.findById(postNo.longValue());

        if (!optionalEntity.isPresent()) {
            throw new IllegalArgumentException();
        }

        return entityToDto(optionalEntity.get());
    }

    /**
     * TODO need to convert from markdown to html before insert
     *
     * @param dto to be put into database
     * @return Long shows how many rows get inserted
     */
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
