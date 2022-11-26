package com.note.hyowon.blog.repository;

import com.note.hyowon.blog.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PostRepository extends JpaRepository<PostEntity, Long>, QuerydslPredicateExecutor<PostEntity> {
}
